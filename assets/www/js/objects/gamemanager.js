Crafty.c('GameManager', {
    init: function() {
        this.requires("2D");
        this.requires("Canvas");

        this.inMotion = false
        this.direction = ''

        this.attr({x: 0, y: 0});
        var self = this;
        Game.gameManager = self;

        self.isBusy = false;

        self.mainTimer = function() {
            setTimeout(function() {
                self.update();
                self.mainTimer();
            }, 500);
        }
        self.mainTimer();

        self.bind('onMoveVertical', self.onMoveVertical);
        self.bind('onMoveHorizontal', self.onMoveHorizontal);
        self.bind('onMoveStopVertical', self.onMoveStopVertical);
        self.bind('onMoveStopHorizontal', self.onMoveStopHorizontal);
    },

    update: function () {
        var self = this;
        if (self.isAllObjectStopped()) {
            //console.log('All object was stopped!');
            if (!self.checkFriends()) {
                if (!self.gravity()) {
                    self.isBusy = false;
                    //console.log('Reset gameManager state');
                }
            }
        }
    },
    
    checkFriends: function() {
        var result = false;
        Game.objects.forEach(function(object) {
            var nearest = object.getNearest();
            if (nearest.length >= 3) {
                nearest.forEach(function (obj) {
                    obj.remove();
                })
                result = true;
            }
        })
        return result;
    },
    
    isHasFriends: function() {
        var result = false;
        Game.objects.forEach(function(object) {
            var nearest = object.getNearest();
            if (nearest.length >= 3) {
                result = true;
            }
        })
        return result;
    },

    isAllObjectStopped: function () {
        var result = true;
        Game.objects.forEach(function(object) {
            if (object.isBusy) {
                result = false;
            }
        });
        return result;
    },

    gravity: function () {
        var self = this;
        var result = false;

        var x = Settings.left;
        while (x < Settings.right) {
            var column = self.getColumn(x);

            if (column.length < 6) {
                result = true;
                self.fillColumn(column, x);
                self.dropItemsFromColumn(column, x);
            }
            x += Settings.poligon;
        }
        return result;
    },

    dropItemsFromColumn: function(column) {
        var y = Settings.bottom - Settings.poligon;
        while (y >= Settings.top) {
            var item = column.pop();
            if (item) {
                item.trigger('StoppedVertical', {
                    x: 0, 
                    y: y - item.y
                });
            }
            y -= Settings.poligon;
        }
    },

    fillColumn: function(column, x) {
        var self = this;
        var y = Settings.top - Settings.poligon;
        while (column.length < 6) {
            var item = self.createItem(x, y);
            column.push(item);
            y -= Settings.poligon;
        }
        column.sort(function (a, b) {
            if (a.y < b.y)
                return -1;
            if (a.y > b.y)
                return 1;
            return 0;
        });
    },

    getColumn: function (x) {
        var column = [];
        Game.objects.forEach(function(obj) {
            if (obj.x == x)
                column.push(obj);
        })
        return column;
    },

    createItem: function (x, y) {
        console.log('Game.createItem')
        var obj = Crafty.e("Face").attr({
            x: x,
            y: y,
            w: Settings.poligon,
            h: Settings.poligon,
            z: 50
        });

        Game.objects.push(obj);
        return obj;
    },

    removeItem: function (item) {
        var index = Game.objects.indexOf(item);
        if (index >= 0) {
            Game.objects.splice(index, 1);
        }
    },

    isObjectOnColumn: function (column, object) {
        var columtLeft = column * Settings.poligon + Settings.left;
        var result = object.center().x > columtLeft;
        result = result && (object.center().x < columtLeft + Settings.poligon);
        return result;
    },

    isObjectOnRow: function (row, object) {
        var rowTop = row * Settings.poligon + Settings.top;
        var result = object.center().y > rowTop;
        result = result && (object.center().y < (rowTop + Settings.poligon));
        return result;
    },

    getColumnNumberByScreenPoint: function (point) {
        return Math.floor((point.x - Settings.left) / Settings.poligon);
    },

    getRowNumberByScreenPoint: function (point) {
        return Math.floor((point.y - Settings.top) / Settings.poligon);
    },

    onMoveVertical: function (touchVector) {
        var startPoint = {
            x: touchVector.startX,
            y: touchVector.startY,
        };
        var column = this.getColumnNumberByScreenPoint(startPoint);
        var self = this;
        Game.objects.forEach(function(object) {
            if (self.isObjectOnColumn(column, object)) {
                object.trigger('MovedVertical', touchVector)
                object.checkBounds();
            }
        })
    },

    onMoveHorizontal: function (touchVector) {
        var startPoint = {
            x: touchVector.startX,
            y: touchVector.startY,
        };

        var row = this.getRowNumberByScreenPoint(startPoint);
        var self = this;
        Game.objects.forEach(function(object) {
            if (self.isObjectOnRow(row, object)) {
                object.trigger('MovedHorizontal', touchVector)
                object.checkBounds();
            }
        })
    },

    onMoveStopVertical: function (touchVector) {
        var startPoint = {
            x: touchVector.startX,
            y: touchVector.startY,
        };
        var column = this.getColumnNumberByScreenPoint(startPoint);
        var self = this;
        Game.objects.forEach(function(object) {
            if (self.isObjectOnColumn(column, object)) {
                object.trigger('StoppedVertical', touchVector)
            }
        })
    },

    onMoveStopHorizontal: function (touchVector) {
        var startPoint = {
            x: touchVector.startX,
            y: touchVector.startY,
        };
        var row = this.getRowNumberByScreenPoint(startPoint);
        var self = this;
        Game.objects.forEach(function(object) {
            if (self.isObjectOnRow(row, object)) {
                object.trigger('StoppedHorizontal', touchVector)
            }
        })
    },
});

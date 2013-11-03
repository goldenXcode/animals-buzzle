Crafty.c('GameManager', {
    init: function() {
        this.requires("2D");
        this.requires("Canvas");

        var self = this;
        Game.gameManager = self;

        self.isBusy = false;
        self.object = [];

        self.itemsPool = new Array();
        for(var i = 0; i < 100; i++) {
            console.log('create item in pool: ' + self.itemsPool.length)
            var obj = Crafty.e("Face").attr({
                x: 0,
                y: 0,
                w: Settings.poligon,
                h: Settings.poligon,
                z: 50,
                alpha: 0
            });
            self.itemsPool.push(obj);
        }

        self.mainTimer = function() {
            setTimeout(function() {
                self.update();
                self.mainTimer();
            }, 1500);
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
                    self.object.forEach(function(object) {
                        object.onUpdate();
                    });
                    self.isBusy = false;
                    //console.log('Reset gameManager state');
                }
            }
        }
    },
    
    getNearestItems: function(obj) {
        var result = [];
        this.object.forEach(function(object) {
            if (obj.isNearest(object)) {
                result.push(object);
            }
        });
        return result;
    },

    checkFriends: function() {
        var result = false;
        var friends = [];
        var self = this;
        self.object.forEach(function(object) {
            var nearest = self.getNearestItems(object);
            if (nearest.length >= 3) {
                friends = friends.concat(nearest);
                result = true;
            }
        })
        friends.forEach(function (obj) {
            Game.gameManager.removeItem(obj);
        })
        return result;
    },
    
    isHasFriends: function() {
        var result = false;
        var self = this;
        self.object.forEach(function(object) {
            var nearest = self.getNearestItems(object);
            if (nearest.length >= 3) {
                result = true;
            }
        })
        return result;
    },

    isAllObjectStopped: function () {
        var result = true;
        this.object.forEach(function(object) {
            if (object.isBusy) {
                result = false;
            }
        });
        return result;
    },

    gravity: function () {
        var self = this;
        var result = false;

        var columnNumber = 0;
        var x = Settings.left;
        while (x < Settings.right) {
            var column = self.getColumn(columnNumber);

            if (column.length < 6) {
                result = true;
                self.fillColumn(column, x);
                self.dropItemsFromColumn(column, x);
            }
            x += Settings.poligon;
            columnNumber += 1;
        }
        return result;
    },

    dropItemsFromColumn: function(column) {
        var y = Settings.bottom - Settings.poligon;
        while (column.length > 0) {
            var item = column.pop();
            item.trigger('StoppedVertical', {
                x: 0, 
                y: Math.floor(y - item.y)
            });
            y -= Settings.poligon;
        }
    },

    fillColumn: function(column, x) {
        var self = this;
        var y = - Settings.poligon;
        while (column.length < 6) {//FIXME: bad condition
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

    getColumn: function (columnNumber) {
        var column = [];
        var self = this;
        self.object.forEach(function(obj) {
            if (self.isObjectOnColumn(columnNumber, obj))
                column.push(obj);
        })
        return column;
    },

    createItem: function (x, y) {
        console.log('Game.createItem');
        this.itemsPool.sort(function () { return rnd(2) - 1})
        var obj = this.itemsPool.pop();

        obj.attr({
            x: Math.floor(x),
            y: Math.floor(y)
        });
        this.object.push(obj);
        obj.onCreate();
        return obj;
    },

    removeItem: function (item) {
        var index = this.object.indexOf(item);
        if (index >= 0) {
            this.object.splice(index, 1);
            this.itemsPool.push(item);
            item.onRemove();
            Game.gameManager.update();
        } else {
            console.log('FUCK! item.alpha=' + item.alpha);
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
        self.object.forEach(function(object) {
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
        self.object.forEach(function(object) {
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
        self.object.forEach(function(object) {
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
        self.object.forEach(function(object) {
            if (self.isObjectOnRow(row, object)) {
                object.trigger('StoppedHorizontal', touchVector)
            }
        })
    },
});

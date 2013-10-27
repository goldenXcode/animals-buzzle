Crafty.c('GameManager', {
    init: function() {
        this.requires("2D");

        this.inMotion = false
        this.direction = ''

        this.attr({x: 0, y: 0, z: 1});
        var self = this;
        Game.gameManager = self;

        self.mainTimer = function() {
            setTimeout(function() {
                if (self.isAllObjectStopped()) {
                    console.log('All object was stopped!');
                    self.gravity();
                }
                self.mainTimer();
            }, 500);
        }
        self.mainTimer();
    },

    isAllObjectStopped: function () {
        Game.objects.forEach(function(object) {
            if (object.isInMotion()) {
                return false;
            }
        });
        return true;
    },

    gravity: function () {
        var self = this;

        var x = Settings.left;
        while (x < Settings.right) {
            var column = self.getColumn(x);

            if (column.length < 6) {
                self.fillColumn(column, x);
                self.dropItemsFromColumn(column, x);
            }
            x += Settings.poligon;
        }
    },

    dropItemsFromColumn: function(column) {
        var y = Settings.bottom - Settings.poligon;
        while (y >= Settings.top) {
            var item = column.pop();
            if (item) {
                item.trigger('Stopped', {x: 0, y: y - item.y});
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
        var obj = Crafty.e("Face").attr({
            x: x,
            y: y,
            w: Settings.poligon,
            h: Settings.poligon
        });

        Game.objects.push(obj);
        return obj;
    }
});

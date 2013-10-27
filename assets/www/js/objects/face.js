Crafty.c('Face', {
    init: function() {
        this.requires("2D");
        this.requires("Canvas");
        this.requires("SpriteAnimation");
        this.requires("Tween")

        function rnd(max) {
            return Math.floor(Math.random() * 1000 * 1000) % max;
        }
        this.animalType = rnd(Game.sprites.length);
        this.requires(Game.sprites[this.animalType]);

        this.attr({
            x: 0,
            y: 0
        });
        this.animate("walk_left", 0, 0, 3);

        var self = this;

        self.isBusy = false;
        self.animateTimer = function() {
            setTimeout(function() {
                self.animate("walk_left", 20, 1);
                self.animateTimer();
            }, rnd(15000) + 4000);
        }
        self.animateTimer();

        this.bind("MovedHorizontal", function(e) {
            //console.log("MovedHorizontal x=" + e.x + ", y=" + e.y);
            self.shift(Math.floor(e.x), 0);
            self.busy();
        });

        this.bind("MovedVertical", function(e) {
            //console.log("MovedVertical x=" + e.x + ", y=" + e.y + ', ' + self.isBusy);
            self.shift(0, Math.floor(e.y));
            self.busy();
        });

        this.bind("StoppedHorizontal", function(e) {
            //console.log("Stopped x=" + e.x + ", y=" + e.y);
            self.busy();
            self.checkStop({
                x: e.x,
                y: 0
            })
        });

        this.bind("StoppedVertical", function(e) {
            //console.log("Stopped x=" + e.x + ", y=" + e.y);
            self.busy();
            self.checkStop({
                x: 0,
                y: e.y
            })
        });
    },

    getOffsetXForPrecisePosition: function () {
        var dx = Math.floor(this.x - Settings.left) % Settings.poligon;
        if (dx > Settings.poligon / 2)
            dx = dx - Settings.poligon;
        return dx;
    },

    getOffsetYForPrecisePosition: function () {
        var dy = Math.floor(this.y - Settings.top) % Settings.poligon;
        if (dy > Settings.poligon / 2)
            dy = dy - Settings.poligon;
        return dy;
    },

    checkStop: function(e) {
        var self = this;
        self.bind('TweenEnd', function() {
            Game.touchManager.checkBounds(self);
            var dx = self.getOffsetXForPrecisePosition();
            var dy = self.getOffsetYForPrecisePosition();
            if (dx != 0 || dy != 0) {
                //console.log('checkStop dx=' + dx + ', dy=' + dy)
                self.tween({
                    x: Math.floor(self.x - dx),
                    y: Math.floor(self.y - dy)
                }, 2)
            } else {
                Game.touchManager.checkBounds(self);
                self.isBusy = false;
                Game.gameManager.update();
            }
        });
        self.tween({
            x: Math.floor(self.x + e.x),
            y: Math.floor(self.y + e.y)
        }, 10)
    },

    isNearest: function(object) {
        var self = this;
        var dx = Math.abs(self.x - object.x);
        var dy = Math.abs(self.y - object.y);
        if (dx <= Settings.poligon) {
            if (dy <= Settings.poligon) {
                if (dy < Settings.poligon/2 || dx < Settings.poligon/2) {
                    return self.animalType == object.animalType;
                }
            }
        }
        return false;
    },

    getNearest: function() {
        var result = [];
        var self = this;
        Game.objects.forEach(function(object) {
            //console.log('object x=' + object.x + ', y=' + object.y)
            if (self.isNearest(object)) {
                result.push(object);
            }
        });
        return result;
    },

    remove: function() {
        var obj = this;
        console.log('remove')
        obj.busy();
        obj.bind('TweenEnd', function() {
            var index = Game.objects.indexOf(obj);
            if (index >= 0) {
                Game.objects.splice(Game.objects.indexOf(obj), 1);
                console.log('Game.objects=' + Game.objects.length)
            }
            console.log('Remove')
            Game.gameManager.update();
        });
        obj.tween({
            alpha: 0.0
        }, 20);
    },

    busy: function () {
        Game.gameManager.isBusy = true;
        this.isBusy = true;
    }
});
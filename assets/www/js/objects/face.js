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

        self.inMotion = false;
        self.animateTimer = function() {
            setTimeout(function() {
                self.animate("walk_left", 20, 1);
                self.animateTimer();
            }, rnd(15000) + 4000);
        }
        self.animateTimer();

        this.bind("MovedHorizontal", function(e) {
            console.log("MovedHorizontal x=" + e.x + ", y=" + e.y);
            self.shift(e.x, 0);
            self.inMotion = true;
        });

        this.bind("MovedVertical", function(e) {
            console.log("MovedVertical x=" + e.x + ", y=" + e.y);
            self.shift(0, e.y);
            self.inMotion = true;
        });

        this.bind("Stopped", function(e) {
            console.log("Stopped x=" + e.x + ", y=" + e.y);
            self.tween({
                x: self.x + e.x,
                y: self.y + e.y
            }, 20)
            self.checkStop()
        });
    },

    checkStop: function() {
        var self = this;
        self.bind('TweenEnd', function() {
            var dx = (self.x - Settings.left) % Settings.poligon;
            var dy = (self.y - Settings.top) % Settings.poligon;
            if (dx != 0 || dy != 0) {
                if (dx > Settings.poligon / 2)
                    dx = dx - Settings.poligon;
                if (dy > Settings.poligon / 2)
                    dy = dy - Settings.poligon;
                self.tween({
                    x: self.x - dx,
                    y: self.y - dy
                }, 20)
            } else {
                Game.touchManager.checkBounds(self);
                self.checkFriends();
                self.unbind('TweenEnd');
                self.inMotion = false;
            }
        });
    },

    isNearest: function(object) {
        var self = this;
        var dx = Math.abs(self.x - object.x);
        var dy = Math.abs(self.y - object.y);
        if (dx <= Settings.poligon) {
            if (dy <= Settings.poligon) {
                if (!dy || !dx) {
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
            if (self.isNearest(object))
                result.push(object);
        });
        return result;
    },

    remove: function(obj) {
        obj.inMotion = true;
        obj.tween({
            alpha: 0.0
        }, 30);
        obj.bind('TweenEnd', function() {
            var index = Game.objects.indexOf(obj);
            if (index >= 0) {
                Game.objects.splice(Game.objects.indexOf(obj), 1);
                console.log('Game.objects=' + Game.objects.length)
            }
            var index = Game.objects.indexOf(obj);
            if (index >= 0) {
                Game.objects.splice(Game.objects.indexOf(obj), 1);
                console.log('Game.objects=' + Game.objects.length)
            }
            obj.inMotion = false;
        })
    },

    checkFriends: function() {
        var self = this;
        Game.objects.forEach(function(object) {
            var nearest = object.getNearest();
            console.log('type=' + object.animalType + ' count=' + nearest.length)
            if (nearest.length >= 3) {
                nearest.forEach(self.remove)
                return true;
            }
        })
        return false;
    },

    isInMotion: function () {
        return this.inMotion;
    }
});
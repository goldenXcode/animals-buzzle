Crafty.c('TouchManager', {
    init: function() {
        this.requires("2D");

        this.inMotion = false;
        this.direction = '';
        this.objects = [];

        this.attr({x: 0, y: 0, z: 1});
        this.attr({
            lastPos: {
                x: 0, 
                y: 0
            }
        });
        var self = this
        Game.touchManager = self
        self.outOfBounds = function (e) {
            if (e.x < Settings.left)
                return true;
            if (e.y < Settings.top)
                return true;
            if (e.x > Settings.right)
                return true;
            if (e.y > Settings.bottom)
                return true;
            return false;
        }
        self.checkBounds = function (object) {
            //var object = this;
            var maxX = Settings.width;
            if (object.x < 0) {
                object.x += Settings.width;
            }
            if (object.x > maxX) {
                object.x -= Settings.width;
            }
            var maxY = Settings.height;
            if (object.y > maxY) {
                object.y -= Settings.height;
            }
            if (object.y < 0) {
                object.y += Settings.height;
            }
        }
        self.setDirection = function (e) {
            var cdx = Math.abs(e.x - self.startPos.x);
            var cdy = Math.abs(e.y - self.startPos.y);
            if (Math.max(cdx, cdy) < 5)
                throw "cantCalcDirectionError";

            if (cdx > cdy) {
                self.direction = 'Horizontal';
                self.isAction = function (object) {
                    var result = object.y < self.startPos.y;
                    result = result && (object.y + object.h > self.startPos.y)
                    return result;                    
                }
            } else {
                self.direction = 'Vertical';
                self.isAction = function (object) {
                    var result = object.x < self.startPos.x;
                    result = result && (object.x + object.w > self.startPos.x);
                    return result;
                }
            }
        }
        self.startMotion = function (e) {
            self.attr({
                lastPos: {
                    x: e.x, 
                    y: e.y
                },
                startPos: {
                    x: e.x, 
                    y: e.y
                },
            });
            self.direction = '';
            self.inMotion = true;
        }
        self.motion = function (e) {         
            if (!self.inMotion)        
                return;
            var dx = e.x - self.lastPos.x;
            var dy = e.y - self.lastPos.y;

            if (self.direction == '') {
                if (Game.gameManager.isBusy) {
                    console.log('Game.gameManager.isBusy!')
                    self.direction = '';
                    self.inMotion = false;
                    return
                }
                self.setDirection(e)
            }

            self.attr({
                lastPos: {
                    x: e.x, 
                    y: e.y
                }
            });

            Game.objects.forEach(function(object) {
                if (self.isAction(object)) {
                    object.trigger('Moved' + self.direction, {
                        x: dx, 
                        y: dy
                    })
                    self.checkBounds(object);
                }
            })
        }
        self.stopMotion = function (e) {
            if (!self.inMotion)
                return

            var dx = (e.x - self.startPos.x) % Settings.poligon;
            var dy = (e.y - self.startPos.y) % Settings.poligon;
            
            if (self.direction == 'Horizontal')
                dy = 0;
            else
                dx = 0;

            if (dx > Settings.poligon / 2)
                dx = dx - Settings.poligon;
            if (dy > Settings.poligon / 2)
                dy = dy - Settings.poligon;

            console.log("Stop move: x=" + dx + ", y=" + dy);
            Game.objects.forEach(function(object) {
                if (self.isAction(object)) {
                    object.trigger('Stopped', {
                        x: -dx, 
                        y: -dy
                    })
                }
            })
            self.direction = '';
            self.inMotion = false;
        }
        Crafty.e("2D, DOM, Color, Mouse")
            .attr({ w: 10000, h: 10000 })
            .bind('MouseDown', function(e) {
                if (Game.gameManager.isBusy)
                    return
                console.log("Mouse down: x=" + e + ", y=" + e.y);
                try {
                    if (!self.outOfBounds(e)) {
                        self.startMotion(e);
                    }
                } catch(error) {
                    console.log("Error: " + error);
                }
            })
            .bind('MouseMove', function(e) {
                //console.log("Mouse move: x=" + e.x + ", y=" + e.y);
                try {
                    if (self.outOfBounds(e)) {
                        self.stopMotion(self.lastPos);
                    } else {
                        self.motion(e);
                    }
                } catch(error) {
                    console.log("Error: " + error);
                }
            })
            .bind('MouseUp', function(e) {
                try {
                    self.stopMotion(e);
                } catch(error) {
                    console.log("Error: " + error);
                }
            })

    },
    
    isCanMoveTo: function(x, y) {
        return true;
        var isCollision = false;
        Crafty("hard_self").each(function(i) {
            if (this.isAt(x, y))
                isCollision = true
        });

        return !isCollision && !this.isUnitOutOfRange(x, y)
    },

    clean: function () {
        this.removeComponent('Unit');
        this.destroy();
    }
});

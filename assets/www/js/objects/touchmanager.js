Crafty.c('TouchManager', {
    init: function() {
        this.requires("2D");

        this.inMotion = false;
        this.direction = '';

        this.attr({
            x: 0, 
            y: 0
        });
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
            if (object.x < Settings.left - Settings.poligon/2) {
                object.x += Settings.width;
            }
            if (object.x > Settings.right - Settings.poligon/2) {
                object.x -= Settings.width;
            }
            if (object.y > Settings.bottom - Settings.poligon/2) {
                object.y -= Settings.height;
            }
            if (object.y < Settings.top - Settings.poligon/2) {
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
                    //console.log('Game.gameManager.isBusy!')
                    self.direction = '';
                    self.inMotion = false;
                    return
                }
                self.setDirection(e)
                self.attr({
                    startPos: {
                        x: e.x, 
                        y: e.y
                    }
                })
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
        Crafty.e("2D, DOM, Color, Mouse")
            .attr({ w: 10000, h: 10000 })
            .bind('MouseDown', function(e) {
                if (Game.gameManager.isBusy)
                    return
                //console.log("Mouse down: x=" + e + ", y=" + e.y);
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

    stopMotion: function (e) {
        var self = this;
        if (!self.inMotion)
            return
        if (!Game.gameManager.isHasFriends()) {
            self.revert();
        } else {
            self.stop(e);
        }
    },
    
    revert: function() {
        var self = this;
        var dx = 2*self.lastPos.x - self.startPos.x;
        var dy = 2*self.lastPos.y - self.startPos.y;
        this.stop({
            x: dx, 
            y: dy
        });
    },

    stop: function (e) {
        var self = this;
        var dx = e.x - self.lastPos.x;
        var dy = e.y - self.lastPos.y;
        
        //console.log("Stop move: x=" + dx + ", y=" + dy);
        Game.objects.forEach(function(object) {
            if (self.isAction(object)) {
                object.trigger('Stopped' + self.direction, {
                    x: -dx, 
                    y: -dy
                })
            }
        })
        self.direction = '';
        self.inMotion = false;
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

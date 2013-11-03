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

        Crafty.e("2D, DOM, Color, Mouse")
            .attr({ w: 10000, h: 10000 })
            .bind('MouseDown', function(e) {
                //console.log("Mouse down: x=" + e + ", y=" + e.y);
                try {
                    if (Game.gameManager.isBusy)
                        throw "Game manager is busy!"
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
            throw "Can't stop without begin called!"
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
        
        Game.gameManager.trigger('onMoveStop' + self.direction, {
            x: -dx, 
            y: -dy,
            startX: self.startPos.x,
            startY:  self.startPos.y
        });
        self.direction = '';
        self.inMotion = false;
    },

    outOfBounds: function (e) {
        if (e.x < Settings.left)
            return true;
        if (e.y < Settings.top)
            return true;
        if (e.x > Settings.right)
            return true;
        if (e.y > Settings.bottom)
            return true;
        return false;
    },

    setDirection: function (e) {
        var self = this;
        var cdx = Math.abs(e.x - self.startPos.x);
        var cdy = Math.abs(e.y - self.startPos.y);
        if (Math.max(cdx, cdy) < 5)
            throw "cantCalcDirectionError";
        self.direction = cdx > cdy ? 'Horizontal': 'Vertical';
    },

    startMotion: function (e) {
        var self = this;

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
    },

    motion: function (e) {  
        var self = this;
        if (!self.inMotion)        
            throw "Can't move without begin called!"
        var dx = e.x - self.lastPos.x;
        var dy = e.y - self.lastPos.y;

        self.checkDirection(e);

        var startPoint = {
            x: dx, 
            y: dy,
            startX: self.startPos.x,
            startY: self.startPos.y
        };
        if (self.direction == 'Vertical') {
            if (!Game.gameManager.isCanMoveVertical(startPoint)) {
                self.stop(e);
                return;
            }
        } else {
            if (!Game.gameManager.isCanMoveHorizontal(startPoint)) {
                self.stop(e);
                return;
            }
        }

        self.attr({
            lastPos: {
                x: e.x, 
                y: e.y
            }
        });
        Game.gameManager.trigger('onMove' + self.direction, {
            x: dx, 
            y: dy,
            startX: self.startPos.x,
            startY:  self.startPos.y
        });
    },

    checkDirection: function (e) {
        var self = this;
        if (self.direction == '') {
            if (Game.gameManager.isBusy) {
                self.direction = '';
                self.inMotion = false;
                throw "Game manager is busy!"
            }
            self.setDirection(e)
            self.attr({
                startPos: {
                    x: e.x, 
                    y: e.y
                }
            })
        }
    }
});

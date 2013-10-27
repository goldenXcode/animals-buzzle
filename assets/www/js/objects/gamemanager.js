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
    }
});

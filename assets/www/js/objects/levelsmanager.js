Crafty.c('LevelsManager', {
    init: function() {
        var self = this;
        self.scope = Game.scope;

        self.mainTimer = function() {
            setTimeout(function() {
                if (scope.scope == self.lastScope) {
                    if (Game.gameManager.countSameObjectsInPool(0)) {
                        
                    }
                }
                self.LastScope = Game.scope.scope;
                self.mainTimer();
            }, 500);
        }
        self.mainTimer();
    },
});

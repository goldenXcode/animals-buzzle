Crafty.c('LockUnit', {
    init: function() {
        this.requires("UnitBehavior");
        this.requires("LockUnitAnimate");
	    this.isCanMove = function() {
	        return false;
	    }
    },

    isCanMove: function() {
        return false;
    },

});

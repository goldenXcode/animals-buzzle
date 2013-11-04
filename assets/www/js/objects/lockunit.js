Crafty.c('LockUnit', {
    init: function() {
        this.requires("UnitBehavior");
        //this.requires("UnitAnimate");
        this.requires("LockUnitAnimate");

	    this.isCanMove = function() {
	        return false;
	    }
    },
});

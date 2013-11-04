Crafty.c('LockUnitAnimate', {
    init: function() {
        this.requires("2D");
        this.requires("Canvas");

        this.animalType = 999;//rnd(Game.sprites.length);
        this.requires('sign');
    }
});

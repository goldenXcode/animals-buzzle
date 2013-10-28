Crafty.c('FaceSprite', {
    init: function() {
        this.requires("2D");
        this.requires("Canvas");
        this.requires("SpriteAnimation");

        this.animalType = rnd(Game.sprites.length);
        this.requires(Game.sprites[this.animalType]);

        this.animate("walk_left", 0, 0, 3);

        var self = this;
        self.animateTimer = function() {
            setTimeout(function() {
                self.animate("walk_left", 20, 1);
                self.animateTimer();
            }, rnd(15000) + 4000);
        }
        self.animateTimer();
    }
});

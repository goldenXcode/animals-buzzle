Crafty.c('Frontground', {
    init: function() {
        this.requires("2D");
        this.requires("Canvas");
        this.requires("frontgroundimg");
        this.attr({
            x: 0, 
            y: 0, 
            w: Crafty.viewport.width, 
            h: Crafty.viewport.height,
            z: 100
        });
    }
});
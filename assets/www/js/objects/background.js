Crafty.c('Background', {
    init: function() {
        this.requires("2D");
        this.requires("Canvas");
        this.requires("background");
        this.attr({
            x: 0, 
            y: 0, 
            w: Crafty.viewport.width, 
            h: Crafty.viewport.height,
            z: 10
        });
        var x = 0;
        while (x < Crafty.viewport.width) {
            var img = Crafty.e("Grass").attr({
                x: x,
                y: Crafty.viewport.height - 100 + rnd(10),
                z: 10
            });
            x += 100 + rnd(20);
        }
        var x = 0;
        while (x < Crafty.viewport.width) {
            var img = Crafty.e("Zabor").attr({
                x: x,
                y: Crafty.viewport.height - 400,
                z: 11
            });
            x += 99;
        }
        var x = 0;
        while (x < Crafty.viewport.width) {
            var img = Crafty.e("Grass").attr({
                x: x,
                y: Crafty.viewport.height - 70 + rnd(10),
                z: 11
            });
            x += 100 + rnd(20);
        }
    }
});
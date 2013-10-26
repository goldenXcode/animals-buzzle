Crafty.scene("main", function() {

    /*var width = Math.min(Crafty.DOM.window.width, Crafty.DOM.window.height)
    Settings.width = width;
    Settings.height = width;
    */
    console.log("Main scene started!")

    Crafty.e("TouchManager");

    for(var i = 0; i*Settings.poligon < Settings.width; i++) {
        console.log("Main scene ground")
        for(var j = 0; j*Settings.poligon < Settings.height; j++) {
    	    console.log("Main scene ground")
            var obj = Crafty.e("Face").attr({
                position: {
                    x: i,
                    y: j
                },
                x: Settings.left + i * Settings.poligon,
                y: Settings.top + j * Settings.poligon,
                w: Settings.poligon,
                h: Settings.poligon
            });

            Game.objects.push(obj);

        }
    }

    console.log("Main scene stoped!")
});

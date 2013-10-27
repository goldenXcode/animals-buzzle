Crafty.scene("main", function() {

    /*var width = Math.min(Crafty.DOM.window.width, Crafty.DOM.window.height)
    Settings.width = width;
    Settings.height = width;
    */
    console.log("Main scene started!")

    Crafty.e("TouchManager");
    Crafty.e("GameManager");

    for(var i = 0; i*Settings.poligon < Settings.width; i++) {
        console.log("Main scene ground")
        for(var j = 0; j*Settings.poligon < Settings.height; j++) {
    	    console.log("Main scene ground")
            Game.gameManager.createItem(
                Settings.left + i * Settings.poligon,
                Settings.top + j * Settings.poligon
                )
        }
    }

    console.log("Main scene stoped!")
});

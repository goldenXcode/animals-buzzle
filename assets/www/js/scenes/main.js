Crafty.scene("main", function() {

    Settings.left = Crafty.viewport.width/2 - Settings.width/2
    Settings.top = Crafty.viewport.height/2 - Settings.height/2
    Settings.right = Settings.left + Settings.width
    Settings.bottom = Settings.top + Settings.height
    console.log("Main scene started!")

    Crafty.e("Background");
    //Crafty.e("Frontground");
    Crafty.e("TouchManager");
    Crafty.e("GameManager");
    Crafty.e("Scope");

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

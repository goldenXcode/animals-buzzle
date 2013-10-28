Crafty.scene("loading", function() {
    // меняем цвет фона
    Crafty.e("Background");

    Settings.left = Crafty.viewport.width/2 - Settings.width/2
    Settings.top = Crafty.viewport.height/2 - Settings.height/2
    Settings.right = Settings.left + Settings.width
    Settings.bottom = Settings.top + Settings.height

    Crafty.e("2D, DOM, Image").attr({
        x: Crafty.viewport.width/2 - 300,
        y: Crafty.viewport.height/2 -300
    }).image('images/splash.png');

    setTimeout(function() {
        Crafty.scene("main");
    }, 1500);
});

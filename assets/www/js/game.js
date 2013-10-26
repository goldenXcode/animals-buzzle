var Settings = {
    top: 50,
    left: 50,
    right: 650,
    bottom: 650,
    width: 600, // ширина игрового поля
    height: 600, // высота
    poligon: 100, // размер полигона 16x16

    scope: 0,
    level: 2, // текущий уровень
    sound: true
};

var Game = {
    scopeView: {},
    level: {},
    sounds: {},
    objects: []
};

var AllScripts = [
    // objects
    'js/objects/face',
    'js/objects/touchmanager',
    // scenes
    'js/scenes/loading',
    'js/scenes/main',
    'js/scenes/win',
    'js/scenes/lose',
    //utils
    'js/storage.js',
    'js/utils.js',
    'js/phonegap-1.4.1.js'
];

require(AllScripts, function() {
    Crafty.init(); // инизиализируем игровое поле
    Settings.sound = Sound().sound;

    Game.level = new Level(Settings);

    // подгружаем спрайт
    Crafty.sprite(Settings.poligon, "images/bear.png", {
        bear: [0,0]
    });
    // подгружаем спрайт
    Crafty.sprite(Settings.poligon, "images/bird.png", {
        bird: [0,0]
    });
    // подгружаем спрайт
    Crafty.sprite(Settings.poligon, "images/bug1.png", {
        bug1: [0,0]
    });
    // подгружаем спрайт
    Crafty.sprite(Settings.poligon, "images/bug2.png", {
        bug2: [0,0]
    });
    // подгружаем спрайт
    Crafty.sprite(Settings.poligon, "images/frog1.png", {
        frog1: [0,0]
    });
    // подгружаем спрайт
    Crafty.sprite(Settings.poligon, "images/frog2.png", {
        frog2: [0,0]
    });

    Game.sprites = [
        'bear',
        'bird',
        'bug1',
        'frog1'
    ]

    var isPhoneGapUse = false;
    if (isPhoneGapUse) {
        Game.sounds.tractor = new Media("/android_asset/www/sounds/tractor.wav")
        Game.sounds.money = new Media("/android_asset/www/sounds/money.wav")
        Game.sounds.namnam = new Media("/android_asset/www/sounds/burp.wav")
        Game.sounds.water = new Media("/android_asset/www/sounds/water.wav")
        Game.sounds.hit = new Media("/android_asset/www/sounds/hit.wav")
        Game.sounds.laught = new Media("/android_asset/www/sounds/laught.wav")
    } else {
        Crafty.audio.add("tractor", "sounds/tractor.wav")
        Crafty.audio.add("money", "sounds/money.wav")
        Crafty.audio.add("burp", "sounds/burp.wav")
        Crafty.audio.add("water", "sounds/water.wav")
        Crafty.audio.add("hit", "sounds/hit.wav")
        Crafty.audio.add("laught", "sounds/laught.wav")

        Game.sounds.tractor = {play: function() {
            Crafty.audio.play("tractor", 1);
        }};
        Game.sounds.money = {play: function() {
            Crafty.audio.play("money", 1);
        }};
        Game.sounds.namnam = {play: function() {
            Crafty.audio.play("burp", 1);
        }};
        Game.sounds.water = {play: function() {
            Crafty.audio.play("water", 1);
        }};
        Game.sounds.hit = {play: function() {
            Crafty.audio.play("hit", 1);
        }};
        Game.sounds.laught = {play: function() {
            Crafty.audio.play("laught", 1);
        }};
    }

    // запускаем первую сцену
    Crafty.scene("loading");
});

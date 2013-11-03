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
};

var AllScripts = [
    // objects
    'js/objects/face',
    'js/objects/touchmanager',
    'js/objects/gamemanager',
    'js/objects/facesprite',
    'js/objects/background',
    'js/objects/frontground',
    'js/objects/zabor',
    'js/objects/grass',
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
    Crafty.sprite(Settings.poligon, "images/bug.png", {
        bug: [0,0]
    });
    // подгружаем спрайт
    Crafty.sprite(Settings.poligon, "images/rabbit.png", {
        rabbit: [0,0]
    });
    // подгружаем спрайт
    Crafty.sprite(Settings.poligon, "images/frog1.png", {
        frog: [0,0]
    });
    // подгружаем спрайт
    Crafty.sprite(Settings.poligon, "images/pig.png", {
        pig: [0,0]
    });

    Crafty.sprite(Settings.poligon, "images/sky.png", {
        background: [0,0]
    });
    Crafty.sprite("images/skyframe.png", {
        frontgroundimg: [0,0]
    });
    Crafty.sprite(100, 400, "images/zabor.png", {
        zabor: [0,0]
    });
    Crafty.sprite(258, 78, "images/grass.png", {
        grass: [0,0]
    });

    Game.sprites = [
        'bear',
        'bird',
        'bug',
        'rabbit',
        'pig',
        'frog'
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

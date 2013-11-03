Crafty.c('UnitManager', {
    init: function() {
        this.requires("2D");
        this.requires("Canvas");

        var self = this;
        self.itemsPool = [];
        for(var i = 0; i < 100; i++) {
            console.log('create item in pool: ' + self.itemsPool.length)
            var obj = Crafty.e("Unit").attr({
                x: 0,
                y: 0,
                w: Settings.poligon,
                h: Settings.poligon,
                z: 50,
                alpha: 0
            });
            this.push(obj);
        }
    },

    popRandom: function() {
        //var index = rnd(this.itemsPool.length);
        //var object = this.itemsPool[index];
        if (rnd(6) == 0) {
            var obj = Crafty.e("LockUnit").attr({
                x: 0,
                y: 0,
                w: Settings.poligon,
                h: Settings.poligon,
                z: 50,
                alpha: 0
            });
            return obj;
        }
        //this.objects.splice(index, 1);
        this.itemsPool.sort(function () { return rnd(2) - 1})
        return this.itemsPool.pop();
    },

    push: function(object) {
        this.itemsPool.push(object);
    }
});
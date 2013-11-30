autowatch = 1;
inlets = 2;
outlets = 2;

mgraphics.init();
mgraphics.relative_coords = 0;
mgraphics.autofill = 0;

var box_height = box.rect[3] - box.rect[1];
var box_width = box.rect[2] - box.rect[0];
var button_down = false;
var current_time = 0.;
var total_time = 0.;

function msg_float(value) {
    if (inlet == 0) {
        if (!button_down) {
            current_time = value;
            if (current_time < 0) {
                current_time = 0;
            } else if (total_time < current_time) {
                current_time = total_time;
            }
        }
        mgraphics.redraw();
        outlet(0, current_time);
    } else if (inlet == 1) {
        if (0 < value) {
            total_time = value; 
        }
        if (total_time < current_time) {
            current_time = total_time;
        }
        mgraphics.redraw();
    }
}

function msg_int(value) {
    msg_float(value)
}

function set(value) {
    if (inlet == 0) {
        if (!button_down) {
            current_time = value;
            if (current_time < 0) {
                current_time = 0;
            } else if (total_time < current_time) {
                current_time = total_time;
            }
        }
    }
    mgraphics.redraw();
}

function onclick (x, y, button, mod1, shift, caps, opt, mod2) {
    button_down = true;
    time = x;
    if (time < 0) {
        time = 0;
    } else if (box_width < time) {
        time = box_width;
    }
    current_time = (time / box_width) * total_time;
    mgraphics.redraw();
    outlet(0, current_time);
}

function ondrag (x, y, button, mod1, shift, caps, opt, mod2) {
    if (button == 1) {
        button_down = true;
    } else {
        button_down = false;
    }
    time = x;
    if (time < 0) {
        time = 0;
    } else if (box_width < time) {
        time = box_width;
    }
    current_time = (time / box_width) * total_time;
    mgraphics.redraw();
    outlet(0, current_time);
}

function onresize(width, height) {
    box_height = height;
    box_width = width;
    box.size(box_width, box_height);
    mgraphics.redraw();
}

function paint() {
    var milliseconds = current_time % 1000;
    var seconds = Math.floor((current_time / 1000) % 60);
    var minutes = Math.floor((current_time / (60 * 1000)) % 60);
    var time_string = ('0'  + minutes).slice(-2) + ':' +
        ('0'  + seconds).slice(-2) + ':' +
        ('00' + milliseconds).slice(-3)
    mgraphics.set_line_width(2);
	mgraphics.set_source_rgba(0.2, 0.2, 0.2, 1);
    mgraphics.rectangle_rounded(0, 0, box_width, box_height, 8, 8);
    mgraphics.fill();
	mgraphics.set_source_rgba(0.8, 0.3, 0.3, 1);
    width = (current_time / total_time) * box_width;
    mgraphics.rectangle_rounded(0, 0, width, box_height, 8, 8);
    mgraphics.fill();
    mgraphics.select_font_face('Arial Bold');
    var font_extents = mgraphics.font_extents();
    var text_extents = mgraphics.text_measure(time_string);
    mgraphics.move_to(
        (box_width / 2) - (text_extents[0] / 2), 
        box_height - ((box_height - font_extents[0]) / 2));
	mgraphics.set_source_rgba(1., 1.0, 1.0, 1);
    mgraphics.show_text(time_string);
}

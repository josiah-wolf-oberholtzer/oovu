autowatch = 1;
inlets = 1;
outlets = 1;

mgraphics.init();
mgraphics.relative_coords = 0;
mgraphics.autofill = 0;

var box_width;
var box_height;
var column_width = 50;
var display_name = 'LOADING...';
var row_height = 20;
var gutter = 10;

onresize(box.rect[2] - box.rect[0], box.rect[3] - box.rect[1]);

function name(new_name) {
	if (!new_name.charAt(0) != '/') {
	    new_name = '/' + new_name;	
	}
    display_name = new_name;
    mgraphics.redraw();	
}

function onresize(width, height) {
	box_width = (Math.round(width / column_width) * column_width) - gutter;
	if (box_width < 0) {
	    box_width = column_width - gutter;	
	}
	box_height = (Math.round(height / row_height) * row_height) - gutter;
	if (box_height < 0) {
	    box_height = row_height - gutter;	
	}
    box.size(box_width, box_height);
    mgraphics.redraw();
}

function paint() {
	mgraphics.set_line_width(2);
	mgraphics.set_source_rgba(0.3, 0.3, 0.3, 1);
    mgraphics.rectangle_rounded(0, 0, box_width, box_height, 8, 8);
    mgraphics.fill();
    mgraphics.set_source_rgba(0.57, 0.61, 0.66, 1);
    mgraphics.move_to(0, 25);
    mgraphics.line_to(box_width, 25);
    mgraphics.stroke();
    mgraphics.set_source_rgba(1, 1, 1, 1);
    mgraphics.move_to(5, 25);
    mgraphics.select_font_face('Arial Bold');
    var extents = mgraphics.font_extents();
    mgraphics.move_to(
        extents[0] / 2,
        25 - ((25 - extents[0]) / 2));
    mgraphics.show_text(display_name);
}
paint.local = 1;


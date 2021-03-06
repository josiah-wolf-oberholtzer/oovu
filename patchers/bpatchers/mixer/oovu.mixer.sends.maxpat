{
	"patcher" : 	{
		"fileversion" : 1,
		"appversion" : 		{
			"major" : 6,
			"minor" : 1,
			"revision" : 5,
			"architecture" : "x86"
		}
,
		"rect" : [ 339.0, 44.0, 396.0, 710.0 ],
		"bgcolor" : [ 1.0, 1.0, 1.0, 0.0 ],
		"bglocked" : 0,
		"openinpresentation" : 1,
		"default_fontsize" : 12.0,
		"default_fontface" : 0,
		"default_fontname" : "Arial",
		"gridonopen" : 0,
		"gridsize" : [ 5.0, 5.0 ],
		"gridsnaponopen" : 0,
		"statusbarvisible" : 2,
		"toolbarvisible" : 1,
		"boxanimatetime" : 200,
		"imprint" : 0,
		"enablehscroll" : 1,
		"enablevscroll" : 1,
		"devicewidth" : 0.0,
		"description" : "",
		"digest" : "",
		"tags" : "",
		"boxes" : [ 			{
				"box" : 				{
					"args" : [ "#1", 8 ],
					"id" : "obj-16",
					"maxclass" : "bpatcher",
					"name" : "oovu.mixer.send.maxpat",
					"numinlets" : 0,
					"numoutlets" : 0,
					"patching_rect" : [ 10.0, 560.0, 190.0, 70.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 0.0, 525.0, 140.0, 70.0 ]
				}

			}
, 			{
				"box" : 				{
					"args" : [ "#1", 7 ],
					"id" : "obj-17",
					"maxclass" : "bpatcher",
					"name" : "oovu.mixer.send.maxpat",
					"numinlets" : 0,
					"numoutlets" : 0,
					"patching_rect" : [ 10.0, 485.0, 190.0, 70.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 0.0, 450.0, 140.0, 70.0 ]
				}

			}
, 			{
				"box" : 				{
					"args" : [ "#1", 6 ],
					"id" : "obj-14",
					"maxclass" : "bpatcher",
					"name" : "oovu.mixer.send.maxpat",
					"numinlets" : 0,
					"numoutlets" : 0,
					"patching_rect" : [ 10.0, 410.0, 190.0, 70.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 0.0, 375.0, 140.0, 70.0 ]
				}

			}
, 			{
				"box" : 				{
					"args" : [ "#1", 5 ],
					"id" : "obj-15",
					"maxclass" : "bpatcher",
					"name" : "oovu.mixer.send.maxpat",
					"numinlets" : 0,
					"numoutlets" : 0,
					"patching_rect" : [ 10.0, 335.0, 190.0, 70.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 0.0, 300.0, 140.0, 70.0 ]
				}

			}
, 			{
				"box" : 				{
					"args" : [ "#1", 4 ],
					"id" : "obj-12",
					"maxclass" : "bpatcher",
					"name" : "oovu.mixer.send.maxpat",
					"numinlets" : 0,
					"numoutlets" : 0,
					"patching_rect" : [ 10.0, 260.0, 190.0, 70.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 0.0, 225.0, 140.0, 70.0 ]
				}

			}
, 			{
				"box" : 				{
					"args" : [ "#1", 3 ],
					"id" : "obj-13",
					"maxclass" : "bpatcher",
					"name" : "oovu.mixer.send.maxpat",
					"numinlets" : 0,
					"numoutlets" : 0,
					"patching_rect" : [ 10.0, 185.0, 190.0, 70.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 0.0, 150.0, 140.0, 70.0 ]
				}

			}
, 			{
				"box" : 				{
					"args" : [ "#1", 2 ],
					"id" : "obj-10",
					"maxclass" : "bpatcher",
					"name" : "oovu.mixer.send.maxpat",
					"numinlets" : 0,
					"numoutlets" : 0,
					"patching_rect" : [ 10.0, 110.0, 190.0, 70.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 0.0, 75.0, 140.0, 70.0 ]
				}

			}
, 			{
				"box" : 				{
					"args" : [ "#1", 1 ],
					"id" : "obj-6",
					"maxclass" : "bpatcher",
					"name" : "oovu.mixer.send.maxpat",
					"numinlets" : 0,
					"numoutlets" : 0,
					"patching_rect" : [ 10.0, 35.0, 190.0, 70.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 0.0, 0.0, 140.0, 70.0 ]
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-1",
					"maxclass" : "newobj",
					"numinlets" : 3,
					"numoutlets" : 3,
					"outlettype" : [ "", "", "" ],
					"patching_rect" : [ 65.0, 10.0, 209.0, 20.0 ],
					"text" : "mxj oovu.Proxy #1 dsp/:sendcount"
				}

			}
 ],
		"lines" : [  ],
		"parameters" : 		{
			"obj-15::obj-4" : [ "live.slider[9]", "live.slider", 0 ],
			"obj-6::obj-4" : [ "live.slider[13]", "live.slider", 0 ],
			"obj-14::obj-4" : [ "live.slider[8]", "live.slider", 0 ],
			"obj-10::obj-4" : [ "live.slider[12]", "live.slider", 0 ],
			"obj-13::obj-4" : [ "live.slider[11]", "live.slider", 0 ],
			"obj-17::obj-4" : [ "live.slider[1]", "live.slider", 0 ],
			"obj-16::obj-4" : [ "live.slider[7]", "live.slider", 0 ],
			"obj-12::obj-4" : [ "live.slider[10]", "live.slider", 0 ]
		}
,
		"dependency_cache" : [ 			{
				"name" : "oovu.mixer.send.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/mixer",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.guibackground.js",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/jsui",
				"patcherrelativepath" : "../../../jsui",
				"type" : "TEXT",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.makeumenu.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/utilities",
				"patcherrelativepath" : "../utilities",
				"type" : "JSON",
				"implicit" : 1
			}
 ]
	}

}

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
		"rect" : [ 344.0, 44.0, 204.0, 655.0 ],
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
					"args" : [ "#1" ],
					"id" : "obj-2",
					"maxclass" : "bpatcher",
					"name" : "oovu.mixer.title.maxpat",
					"numinlets" : 0,
					"numoutlets" : 0,
					"patching_rect" : [ 0.0, 0.0, 190.0, 20.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 0.0, 0.0, 190.0, 20.0 ]
				}

			}
, 			{
				"box" : 				{
					"args" : [ "#1" ],
					"id" : "obj-1",
					"maxclass" : "bpatcher",
					"name" : "oovu.mixer.sends.maxpat",
					"numinlets" : 0,
					"numoutlets" : 0,
					"patching_rect" : [ 0.0, 25.0, 190.0, 615.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 0.0, 25.0, 190.0, 615.0 ]
				}

			}
 ],
		"lines" : [  ],
		"parameters" : 		{
			"obj-1::obj-15::obj-4" : [ "live.slider[4]", "live.slider", 0 ],
			"obj-1::obj-14::obj-4" : [ "live.slider[5]", "live.slider", 0 ],
			"obj-1::obj-13::obj-4" : [ "live.slider[2]", "live.slider", 0 ],
			"obj-1::obj-6::obj-4" : [ "live.slider", "live.slider", 0 ],
			"obj-1::obj-17::obj-4" : [ "live.slider[6]", "live.slider", 0 ],
			"obj-1::obj-3" : [ "live.numbox", "live.numbox", 0 ],
			"obj-1::obj-12::obj-4" : [ "live.slider[3]", "live.slider", 0 ],
			"obj-1::obj-16::obj-4" : [ "live.slider[7]", "live.slider", 0 ],
			"obj-1::obj-10::obj-4" : [ "live.slider[1]", "live.slider", 0 ]
		}
,
		"dependency_cache" : [ 			{
				"name" : "oovu.mixer.sends.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/mixer",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
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
, 			{
				"name" : "oovu.mixer.title.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/mixer",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
 ]
	}

}

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
		"rect" : [ 29.0, 69.0, 640.0, 480.0 ],
		"bglocked" : 0,
		"openinpresentation" : 0,
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
					"args" : [ "events" ],
					"id" : "obj-1",
					"maxclass" : "bpatcher",
					"name" : "omod.eventscript.maxpat",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 0.0, 0.0, 140.0, 210.0 ]
				}

			}
 ],
		"lines" : [  ],
		"dependency_cache" : [ 			{
				"name" : "omod.eventscript.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/modules/omod.eventscript",
				"patcherrelativepath" : "../../patchers/modules/omod.eventscript",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.eventscript.model.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/modules/omod.eventscript",
				"patcherrelativepath" : "../../patchers/modules/omod.eventscript",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.eventscript.view.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/modules/omod.eventscript",
				"patcherrelativepath" : "../../patchers/modules/omod.eventscript",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.guibackground.js",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/jsui",
				"patcherrelativepath" : "../../jsui",
				"type" : "TEXT",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.makeumenu.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/utilities",
				"patcherrelativepath" : "../../patchers/abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
 ]
	}

}

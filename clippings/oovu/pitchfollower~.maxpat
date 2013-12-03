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
					"args" : [ "pitch~" ],
					"id" : "obj-2",
					"maxclass" : "bpatcher",
					"name" : "omod.pitchfollower~.maxpat",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 0.0, 0.0, 240.0, 210.0 ]
				}

			}
 ],
		"lines" : [  ],
		"parameters" : 		{
			"obj-2::obj-13::obj-6" : [ "live.slider[31]", "live.slider", 0 ]
		}
,
		"dependency_cache" : [ 			{
				"name" : "omod.pitchfollower~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/modules/omod.pitchfollower~",
				"patcherrelativepath" : "../../patchers/modules/omod.pitchfollower~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.pitchfollower~.model.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/modules/omod.pitchfollower~",
				"patcherrelativepath" : "../../patchers/modules/omod.pitchfollower~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.bundle.dsp.receiver.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/bundles",
				"patcherrelativepath" : "../../patchers/abstractions/bundles",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.destination.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/pushpull",
				"patcherrelativepath" : "../../patchers/abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.destination.poly~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/pushpull",
				"patcherrelativepath" : "../../patchers/abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.dsp.8chlevels.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/dsp",
				"patcherrelativepath" : "../../patchers/abstractions/dsp",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.pitchfollower~.voix.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/modules/omod.pitchfollower~",
				"patcherrelativepath" : "../../patchers/modules/omod.pitchfollower~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.pitchfollower~.view.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/modules/omod.pitchfollower~",
				"patcherrelativepath" : "../../patchers/modules/omod.pitchfollower~",
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
				"name" : "oovu.gui.meters.8x.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/gui",
				"patcherrelativepath" : "../../patchers/abstractions/gui",
				"type" : "JSON",
				"implicit" : 1
			}
 ]
	}

}

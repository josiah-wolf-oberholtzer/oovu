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
		"rect" : [ 454.0, 134.0, 640.0, 480.0 ],
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
					"args" : [ "output~" ],
					"id" : "obj-2",
					"maxclass" : "bpatcher",
					"name" : "omod.output~.maxpat",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 160.0, 10.0, 140.0, 210.0 ]
				}

			}
, 			{
				"box" : 				{
					"args" : [ "sfplay~" ],
					"id" : "obj-1",
					"maxclass" : "bpatcher",
					"name" : "omod.sfplay~.maxpat",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 10.0, 10.0, 140.0, 210.0 ]
				}

			}
 ],
		"lines" : [  ],
		"parameters" : 		{
			"obj-1::obj-13::obj-18::obj-18::obj-4" : [ "live.slider", "live.slider", 0 ],
			"obj-2::obj-13::obj-6" : [ "live.slider[31]", "live.slider", 0 ],
			"obj-1::obj-13::obj-17" : [ "live.numbox", "live.numbox", 0 ],
			"obj-1::obj-13::obj-12" : [ "live.slider[1]", "live.slider", 0 ],
			"obj-1::obj-13::obj-18::obj-204" : [ "live.numbox[13]", "live.numbox", 0 ],
			"obj-2::obj-13::obj-204" : [ "live.numbox[73]", "live.numbox", 0 ],
			"obj-2::obj-13::obj-36" : [ "live.numbox[72]", "live.numbox[1]", 0 ]
		}
,
		"dependency_cache" : [ 			{
				"name" : "omod.sfplay~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/modules/omod.sfplay~",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.sfplay~.model.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/modules/omod.sfplay~",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.bundle.dsp.sender.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/bundles",
				"patcherrelativepath" : "../../abstractions/bundles",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.source.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/pushpull",
				"patcherrelativepath" : "../../abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.source.poly~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/pushpull",
				"patcherrelativepath" : "../../abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.dsp.8chlevels.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/dsp",
				"patcherrelativepath" : "../../abstractions/dsp",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.sfplay~.voix.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/modules/omod.sfplay~",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.dsp.thispoly~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/dsp",
				"patcherrelativepath" : "../../abstractions/dsp",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.sfplay~.view.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/modules/omod.sfplay~",
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
				"name" : "oovu.gui.meters.8x.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/gui",
				"patcherrelativepath" : "../../abstractions/gui",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.gui.send.dynamic.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/gui",
				"patcherrelativepath" : "../../abstractions/gui",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.gui.send.1x.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/gui",
				"patcherrelativepath" : "../../abstractions/gui",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.makeumenu.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/utilities",
				"patcherrelativepath" : "../../abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.timeslider.js",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/jsui",
				"patcherrelativepath" : "../../../jsui",
				"type" : "TEXT",
				"implicit" : 1
			}
, 			{
				"name" : "omod.output~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/modules/omod.output~",
				"patcherrelativepath" : "../omod.output~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.output~.model.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/modules/omod.output~",
				"patcherrelativepath" : "../omod.output~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.bundle.dsp.receiver.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/bundles",
				"patcherrelativepath" : "../../abstractions/bundles",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.destination.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/pushpull",
				"patcherrelativepath" : "../../abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.destination.poly~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/pushpull",
				"patcherrelativepath" : "../../abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.output~.voix.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/modules/omod.output~",
				"patcherrelativepath" : "../omod.output~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.output~.view.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/modules/omod.output~",
				"patcherrelativepath" : "../omod.output~",
				"type" : "JSON",
				"implicit" : 1
			}
 ]
	}

}

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
		"rect" : [ 410.0, 126.0, 640.0, 480.0 ],
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
					"args" : [ "panner~" ],
					"id" : "obj-1",
					"maxclass" : "bpatcher",
					"name" : "omod.panner~.maxpat",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 0.0, 0.0, 140.0, 210.0 ]
				}

			}
 ],
		"lines" : [  ],
		"parameters" : 		{
			"obj-1::obj-13::obj-18::obj-204" : [ "live.numbox[13]", "live.numbox", 0 ],
			"obj-1::obj-13::obj-38" : [ "live.numbox[17]", "live.numbox[4]", 0 ],
			"obj-1::obj-13::obj-12" : [ "live.slider[1]", "live.slider", 0 ],
			"obj-1::obj-13::obj-18::obj-18::obj-4" : [ "live.slider", "live.slider", 0 ],
			"obj-1::obj-13::obj-6" : [ "live.slider[6]", "live.slider", 0 ],
			"obj-1::obj-13::obj-204" : [ "live.numbox[14]", "live.numbox", 0 ]
		}
,
		"dependency_cache" : [ 			{
				"name" : "omod.panner~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/modules/omod.panner~",
				"patcherrelativepath" : "../../patchers/modules/omod.panner~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.panner~.model.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/modules/omod.panner~",
				"patcherrelativepath" : "../../patchers/modules/omod.panner~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.bundle.dsp.sender.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/bundles",
				"patcherrelativepath" : "../../patchers/abstractions/bundles",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.source.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/pushpull",
				"patcherrelativepath" : "../../patchers/abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.source.poly~.maxpat",
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
				"name" : "omod.panner~.voix.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/modules/omod.panner~",
				"patcherrelativepath" : "../../patchers/modules/omod.panner~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.dsp.panner~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/dsp",
				"patcherrelativepath" : "../../patchers/abstractions/dsp",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.dsp.thispoly~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/dsp",
				"patcherrelativepath" : "../../patchers/abstractions/dsp",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.bundle.dsp.poly~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/bundles",
				"patcherrelativepath" : "../../patchers/abstractions/bundles",
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
				"name" : "omod.panner~.view.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/modules/omod.panner~",
				"patcherrelativepath" : "../../patchers/modules/omod.panner~",
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
, 			{
				"name" : "oovu.gui.send.dynamic.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/gui",
				"patcherrelativepath" : "../../patchers/abstractions/gui",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.gui.send.1x.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/gui",
				"patcherrelativepath" : "../../patchers/abstractions/gui",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.makeumenu.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/patchers/abstractions/utilities",
				"patcherrelativepath" : "../../patchers/abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "ambipanning~.mxo",
				"type" : "iLaX"
			}
, 			{
				"name" : "ambimonitor.mxo",
				"type" : "iLaX"
			}
 ]
	}

}

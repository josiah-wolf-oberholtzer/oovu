{
	"patcher" : 	{
		"fileversion" : 1,
		"appversion" : 		{
			"major" : 6,
			"minor" : 1,
			"revision" : 3,
			"architecture" : "x86"
		}
,
		"rect" : [ 370.0, 48.0, 340.0, 344.0 ],
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
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-4",
					"maxclass" : "newobj",
					"numinlets" : 2,
					"numoutlets" : 3,
					"outlettype" : [ "", "", "" ],
					"patching_rect" : [ 15.0, 295.0, 100.0, 20.0 ],
					"text" : "mxj oovu.Root"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-2",
					"maxclass" : "message",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 15.0, 10.0, 75.0, 18.0 ],
					"text" : "/input/:show"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-3",
					"maxclass" : "message",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 15.0, 255.0, 305.0, 18.0 ],
					"text" : "/stutter/transposition -6."
				}

			}
, 			{
				"box" : 				{
					"args" : [ "allpass~" ],
					"id" : "obj-1",
					"maxclass" : "bpatcher",
					"name" : "omod.allpass~.maxpat",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 15.0, 40.0, 240.0, 210.0 ]
				}

			}
 ],
		"lines" : [ 			{
				"patchline" : 				{
					"destination" : [ "obj-3", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-1", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-1", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-2", 0 ]
				}

			}
 ],
		"parameters" : 		{
			"obj-1::obj-5::obj-18::obj-18::obj-4" : [ "live.slider", "live.slider", 0 ],
			"obj-1::obj-5::obj-36" : [ "live.numbox[43]", "live.numbox[4]", 0 ],
			"obj-1::obj-5::obj-12" : [ "live.slider[14]", "live.slider", 0 ],
			"obj-1::obj-5::obj-21" : [ "live.numbox[12]", "live.numbox[4]", 0 ],
			"obj-1::obj-5::obj-204" : [ "live.numbox[46]", "live.numbox", 0 ],
			"obj-1::obj-5::obj-26" : [ "live.numbox[44]", "live.numbox[4]", 0 ],
			"obj-1::obj-5::obj-6" : [ "live.slider[15]", "live.slider", 0 ],
			"obj-1::obj-5::obj-31" : [ "live.numbox[42]", "live.numbox[4]", 0 ],
			"obj-1::obj-5::obj-49" : [ "live.numbox[39]", "live.numbox[4]", 0 ],
			"obj-1::obj-5::obj-18::obj-204" : [ "live.numbox[3]", "live.numbox", 0 ],
			"obj-1::obj-5::obj-40" : [ "live.numbox[40]", "live.numbox[4]", 0 ],
			"obj-1::obj-5::obj-38" : [ "live.numbox[41]", "live.numbox[4]", 0 ]
		}
,
		"dependency_cache" : [ 			{
				"name" : "omod.allpass~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/modules/omod.allpass~",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.allpass~.model.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/modules/omod.allpass~",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.bundle.dsp.sender.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/abstractions/bundles",
				"patcherrelativepath" : "../../abstractions/bundles",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.source.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/abstractions/pushpull",
				"patcherrelativepath" : "../../abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.source.poly~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/abstractions/pushpull",
				"patcherrelativepath" : "../../abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.dsp.8chlevels.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/abstractions/dsp",
				"patcherrelativepath" : "../../abstractions/dsp",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.bundle.dsp.base.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/abstractions/bundles",
				"patcherrelativepath" : "../../abstractions/bundles",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.allpass~.voix.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/modules/omod.allpass~",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.allpass~.voix.kernelwrapper.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/modules/omod.allpass~",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.allpass~.voix.kernel.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/modules/omod.allpass~",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.dsp.poly.1x1.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/abstractions/dsp",
				"patcherrelativepath" : "../../abstractions/dsp",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.bundle.dsp.poly~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/abstractions/bundles",
				"patcherrelativepath" : "../../abstractions/bundles",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.bundle.dsp.receiver.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/abstractions/bundles",
				"patcherrelativepath" : "../../abstractions/bundles",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.destination.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/abstractions/pushpull",
				"patcherrelativepath" : "../../abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.destination.poly~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/abstractions/pushpull",
				"patcherrelativepath" : "../../abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.allpass~.view.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/modules/omod.allpass~",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.guibackground.js",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/abstractions/gui",
				"patcherrelativepath" : "../../abstractions/gui",
				"type" : "TEXT",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.gui.meters.8x.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/abstractions/gui",
				"patcherrelativepath" : "../../abstractions/gui",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.gui.send.dynamic.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/abstractions/gui",
				"patcherrelativepath" : "../../abstractions/gui",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.gui.send.1x.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/abstractions/gui",
				"patcherrelativepath" : "../../abstractions/gui",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.makeumenu.maxpat",
				"bootpath" : "/Users/josiah/Documents/Development/oovu/abstractions/utilities",
				"patcherrelativepath" : "../../abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
 ]
	}

}

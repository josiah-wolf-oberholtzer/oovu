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
		"rect" : [ 288.0, 392.0, 567.0, 294.0 ],
		"bgcolor" : [ 0.0, 0.0, 0.0, 1.0 ],
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
					"id" : "obj-15",
					"maxclass" : "message",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 310.0, 10.0, 50.0, 18.0 ],
					"text" : ":show"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-7",
					"maxclass" : "message",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 310.0, 255.0, 246.0, 18.0 ],
					"text" : "/dsp/active 0"
				}

			}
, 			{
				"box" : 				{
					"args" : [ "allpasser~" ],
					"id" : "obj-14",
					"maxclass" : "bpatcher",
					"name" : "omod.allpass~.maxpat",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 310.0, 35.0, 240.0, 210.0 ]
				}

			}
, 			{
				"box" : 				{
					"args" : [ "out~" ],
					"id" : "obj-2",
					"maxclass" : "bpatcher",
					"name" : "omod.output~.maxpat",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 160.0, 35.0, 140.0, 210.0 ]
				}

			}
, 			{
				"box" : 				{
					"args" : [ "in~" ],
					"id" : "obj-1",
					"maxclass" : "bpatcher",
					"name" : "omod.input~.maxpat",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 10.0, 35.0, 140.0, 210.0 ]
				}

			}
 ],
		"lines" : [ 			{
				"patchline" : 				{
					"destination" : [ "obj-7", 1 ],
					"disabled" : 0,
					"hidden" : 0,
					"midpoints" : [ 319.5, 249.5, 546.5, 249.5 ],
					"source" : [ "obj-14", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-14", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-15", 0 ]
				}

			}
 ],
		"parameters" : 		{
			"obj-1::obj-13::obj-56::obj-42" : [ "live.numbox[1]", "live.numbox", 0 ],
			"obj-1::obj-13::obj-11::obj-18::obj-4" : [ "live.slider", "live.slider", 0 ],
			"obj-14::obj-5::obj-49" : [ "live.numbox[39]", "live.numbox[4]", 0 ],
			"obj-2::obj-13::obj-36" : [ "live.numbox[6]", "live.numbox[1]", 0 ],
			"obj-14::obj-5::obj-31" : [ "live.numbox[42]", "live.numbox[4]", 0 ],
			"obj-14::obj-5::obj-204" : [ "live.numbox[46]", "live.numbox", 0 ],
			"obj-2::obj-13::obj-204" : [ "live.numbox[15]", "live.numbox", 0 ],
			"obj-1::obj-13::obj-62::obj-51" : [ "live.numbox[4]", "live.numbox", 0 ],
			"obj-14::obj-5::obj-21" : [ "live.numbox[12]", "live.numbox[4]", 0 ],
			"obj-1::obj-13::obj-56::obj-33" : [ "live.tab[3]", "live.tab[1]", 0 ],
			"obj-14::obj-5::obj-40" : [ "live.numbox[40]", "live.numbox[4]", 0 ],
			"obj-14::obj-5::obj-18::obj-204" : [ "live.numbox[7]", "live.numbox", 0 ],
			"obj-1::obj-13::obj-56::obj-41" : [ "live.numbox", "live.numbox", 0 ],
			"obj-1::obj-13::obj-9" : [ "live.tab", "live.tab", 0 ],
			"obj-1::obj-13::obj-11::obj-204" : [ "live.numbox[5]", "live.numbox", 0 ],
			"obj-14::obj-5::obj-26" : [ "live.numbox[44]", "live.numbox[4]", 0 ],
			"obj-1::obj-13::obj-58::obj-21" : [ "live.numbox[2]", "live.numbox", 0 ],
			"obj-14::obj-5::obj-12" : [ "live.slider[14]", "live.slider", 0 ],
			"obj-2::obj-13::obj-6" : [ "live.slider[7]", "live.slider", 0 ],
			"obj-1::obj-13::obj-62::obj-36" : [ "live.numbox[3]", "live.numbox[1]", 0 ],
			"obj-1::obj-13::obj-6" : [ "live.slider[3]", "live.slider", 0 ],
			"obj-14::obj-5::obj-6" : [ "live.slider[15]", "live.slider", 0 ],
			"obj-14::obj-5::obj-38" : [ "live.numbox[41]", "live.numbox[4]", 0 ],
			"obj-14::obj-5::obj-36" : [ "live.numbox[43]", "live.numbox[4]", 0 ],
			"obj-14::obj-5::obj-18::obj-18::obj-4" : [ "live.slider[1]", "live.slider", 0 ]
		}
,
		"dependency_cache" : [ 			{
				"name" : "omod.input~.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.input~",
				"patcherrelativepath" : "../omod.input~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.input~.model.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.input~",
				"patcherrelativepath" : "../omod.input~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.bundle.dsp.sender.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/abstractions/bundles",
				"patcherrelativepath" : "../../abstractions/bundles",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.source.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/abstractions/pushpull",
				"patcherrelativepath" : "../../abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.source.poly~.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/abstractions/pushpull",
				"patcherrelativepath" : "../../abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.dsp.8chlevels.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/abstractions/dsp",
				"patcherrelativepath" : "../../abstractions/dsp",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.input~.voix.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.input~",
				"patcherrelativepath" : "../omod.input~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.dsp.thispoly~.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/abstractions/dsp",
				"patcherrelativepath" : "../../abstractions/dsp",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.input~.view.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.input~",
				"patcherrelativepath" : "../omod.input~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.guibackground.js",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/abstractions/gui",
				"patcherrelativepath" : "../../abstractions/gui",
				"type" : "TEXT",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.gui.meters.8x.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/abstractions/gui",
				"patcherrelativepath" : "../../abstractions/gui",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.input~.view.oscillator.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.input~",
				"patcherrelativepath" : "../omod.input~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "sine.svg",
				"bootpath" : "/Applications/Max 6.1/patches/picts/m4l-picts",
				"patcherrelativepath" : "../../../../../patches/picts/m4l-picts",
				"type" : "svg ",
				"implicit" : 1
			}
, 			{
				"name" : "saw.svg",
				"bootpath" : "/Applications/Max 6.1/patches/picts/m4l-picts",
				"patcherrelativepath" : "../../../../../patches/picts/m4l-picts",
				"type" : "svg ",
				"implicit" : 1
			}
, 			{
				"name" : "square.svg",
				"bootpath" : "/Applications/Max 6.1/patches/picts/m4l-picts",
				"patcherrelativepath" : "../../../../../patches/picts/m4l-picts",
				"type" : "svg ",
				"implicit" : 1
			}
, 			{
				"name" : "random.svg",
				"bootpath" : "/Applications/Max 6.1/patches/picts/m4l-picts",
				"patcherrelativepath" : "../../../../../patches/picts/m4l-picts",
				"type" : "svg ",
				"implicit" : 1
			}
, 			{
				"name" : "omod.input~.view.soundfile.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.input~",
				"patcherrelativepath" : "../omod.input~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.input~.view.adc.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.input~",
				"patcherrelativepath" : "../omod.input~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.gui.send.dynamic.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/abstractions/gui",
				"patcherrelativepath" : "../../abstractions/gui",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.gui.send.1x.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/abstractions/gui",
				"patcherrelativepath" : "../../abstractions/gui",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.makeumenu.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/abstractions/utilities",
				"patcherrelativepath" : "../../abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.output~.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.output~",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.output~.model.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.output~",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.bundle.dsp.receiver.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/abstractions/bundles",
				"patcherrelativepath" : "../../abstractions/bundles",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.destination.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/abstractions/pushpull",
				"patcherrelativepath" : "../../abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.destination.poly~.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/abstractions/pushpull",
				"patcherrelativepath" : "../../abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.output~.voix.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.output~",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.bundle.dsp.base.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/abstractions/bundles",
				"patcherrelativepath" : "../../abstractions/bundles",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.output~.view.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.output~",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.allpass~.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.allpass~",
				"patcherrelativepath" : "../omod.allpass~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.allpass~.model.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.allpass~",
				"patcherrelativepath" : "../omod.allpass~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.allpass~.voix.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.allpass~",
				"patcherrelativepath" : "../omod.allpass~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.allpass~.voix.kernelwrapper.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.allpass~",
				"patcherrelativepath" : "../omod.allpass~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.allpass~.voix.kernel.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.allpass~",
				"patcherrelativepath" : "../omod.allpass~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.dsp.poly.1x1.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/abstractions/dsp",
				"patcherrelativepath" : "../../abstractions/dsp",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.bundle.dsp.poly~.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/abstractions/bundles",
				"patcherrelativepath" : "../../abstractions/bundles",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.allpass~.view.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.allpass~",
				"patcherrelativepath" : "../omod.allpass~",
				"type" : "JSON",
				"implicit" : 1
			}
 ]
	}

}

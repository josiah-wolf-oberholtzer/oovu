{
	"patcher" : 	{
		"fileversion" : 1,
		"appversion" : 		{
			"major" : 6,
			"minor" : 1,
			"revision" : 6,
			"architecture" : "x86"
		}
,
		"rect" : [ 495.0, 44.0, 502.0, 615.0 ],
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
					"frgb" : 0.0,
					"id" : "obj-28",
					"maxclass" : "comment",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 160.0, 555.0, 21.0, 20.0 ],
					"presentation_rect" : [ 160.0, 554.0, 0.0, 0.0 ],
					"text" : "9)",
					"textcolor" : [ 1.0, 1.0, 1.0, 1.0 ]
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"frgb" : 0.0,
					"id" : "obj-29",
					"maxclass" : "comment",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 160.0, 530.0, 21.0, 20.0 ],
					"presentation_rect" : [ 160.0, 529.0, 0.0, 0.0 ],
					"text" : "8)",
					"textcolor" : [ 1.0, 1.0, 1.0, 1.0 ]
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"frgb" : 0.0,
					"id" : "obj-30",
					"maxclass" : "comment",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 160.0, 505.0, 21.0, 20.0 ],
					"presentation_rect" : [ 160.0, 504.0, 0.0, 0.0 ],
					"text" : "7)",
					"textcolor" : [ 1.0, 1.0, 1.0, 1.0 ]
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-27",
					"maxclass" : "message",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 180.0, 480.0, 149.0, 18.0 ],
					"presentation_rect" : [ 225.0, 479.0, 0.0, 0.0 ],
					"text" : "/input~/oscillator/pulsing 1"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-19",
					"maxclass" : "message",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 180.0, 455.0, 185.0, 18.0 ],
					"presentation_rect" : [ 180.0, 455.0, 0.0, 0.0 ],
					"text" : "/input~/oscillator/waveform noise"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-11",
					"maxclass" : "message",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 180.0, 430.0, 137.0, 18.0 ],
					"presentation_rect" : [ 180.0, 428.0, 0.0, 0.0 ],
					"text" : "/input~/source oscillator"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-17",
					"maxclass" : "newobj",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 340.0, 310.0, 85.0, 20.0 ],
					"text" : "print /output~"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-25",
					"maxclass" : "newobj",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 20.0, 310.0, 85.0, 20.0 ],
					"text" : "print /input~"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-26",
					"maxclass" : "newobj",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 180.0, 310.0, 85.0, 20.0 ],
					"text" : "print /allpass~"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"frgb" : 0.0,
					"id" : "obj-24",
					"maxclass" : "comment",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 160.0, 480.0, 21.0, 20.0 ],
					"text" : "6)",
					"textcolor" : [ 1.0, 1.0, 1.0, 1.0 ]
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"frgb" : 0.0,
					"id" : "obj-23",
					"maxclass" : "comment",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 160.0, 455.0, 21.0, 20.0 ],
					"text" : "5)",
					"textcolor" : [ 1.0, 1.0, 1.0, 1.0 ]
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"frgb" : 0.0,
					"id" : "obj-22",
					"maxclass" : "comment",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 160.0, 430.0, 21.0, 20.0 ],
					"text" : "4)",
					"textcolor" : [ 1.0, 1.0, 1.0, 1.0 ]
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"frgb" : 0.0,
					"id" : "obj-21",
					"maxclass" : "comment",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 160.0, 405.0, 21.0, 20.0 ],
					"text" : "3)",
					"textcolor" : [ 1.0, 1.0, 1.0, 1.0 ]
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"frgb" : 0.0,
					"id" : "obj-9",
					"maxclass" : "comment",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 160.0, 380.0, 21.0, 20.0 ],
					"text" : "2)",
					"textcolor" : [ 1.0, 1.0, 1.0, 1.0 ]
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"frgb" : 0.0,
					"id" : "obj-18",
					"maxclass" : "comment",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 160.0, 355.0, 21.0, 20.0 ],
					"text" : "1)",
					"textcolor" : [ 1.0, 1.0, 1.0, 1.0 ]
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-10",
					"maxclass" : "message",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 180.0, 405.0, 122.0, 18.0 ],
					"text" : "/output~/dac/active 1"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-20",
					"maxclass" : "message",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 180.0, 555.0, 153.0, 18.0 ],
					"text" : "/*/dsp/output.1/amplitude 1"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-16",
					"maxclass" : "message",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 180.0, 530.0, 288.0, 18.0 ],
					"text" : "/panner~/dsp/output.1/destination /output~/dsp/input"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-15",
					"maxclass" : "message",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 180.0, 505.0, 281.0, 18.0 ],
					"text" : "/input~/dsp/output.1/destination /panner~/dsp/input"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-14",
					"maxclass" : "message",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 180.0, 380.0, 151.0, 18.0 ],
					"text" : "/output~/dsp/:voicecount 2"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-13",
					"maxclass" : "message",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 180.0, 355.0, 86.0, 18.0 ],
					"text" : "/*/dsp/active 1"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-12",
					"maxclass" : "newobj",
					"numinlets" : 2,
					"numoutlets" : 3,
					"outlettype" : [ "", "", "" ],
					"patching_rect" : [ 180.0, 580.0, 87.0, 20.0 ],
					"text" : "mxj oovu.Root"
				}

			}
, 			{
				"box" : 				{
					"fontface" : 1,
					"fontname" : "Arial",
					"fontsize" : 42.0,
					"frgb" : 0.0,
					"id" : "obj-8",
					"maxclass" : "comment",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 5.0, 0.0, 135.0, 53.0 ],
					"text" : "OOVU",
					"textcolor" : [ 0.8, 0.8, 0.8, 1.0 ]
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"frgb" : 0.0,
					"id" : "obj-5",
					"maxclass" : "comment",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 135.0, 25.0, 314.0, 20.0 ],
					"text" : "random 8-channel ambisonics panner with Doppler effect"
				}

			}
, 			{
				"box" : 				{
					"fontface" : 1,
					"fontname" : "Arial",
					"fontsize" : 16.0,
					"frgb" : 0.0,
					"id" : "obj-6",
					"maxclass" : "comment",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 135.0, 5.0, 123.0, 24.0 ],
					"text" : "omod.panner~",
					"underline" : 1
				}

			}
, 			{
				"box" : 				{
					"bgcolor" : [ 1.0, 1.0, 1.0, 1.0 ],
					"bordercolor" : [ 0.094118, 0.113725, 0.137255, 1.0 ],
					"grad1" : [ 0.666667, 0.698039, 0.717647, 1.0 ],
					"grad2" : [ 0.094118, 0.113725, 0.137255, 1.0 ],
					"id" : "obj-7",
					"maxclass" : "panel",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 0.0, 0.0, 500.0, 50.0 ],
					"prototypename" : "M4L.horizontal-black",
					"rounded" : 0
				}

			}
, 			{
				"box" : 				{
					"args" : [ "input~" ],
					"id" : "obj-2",
					"maxclass" : "bpatcher",
					"name" : "omod.input~.maxpat",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 20.0, 80.0, 140.0, 210.0 ]
				}

			}
, 			{
				"box" : 				{
					"args" : [ "output~" ],
					"id" : "obj-3",
					"maxclass" : "bpatcher",
					"name" : "omod.output~.maxpat",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 340.0, 80.0, 140.0, 210.0 ]
				}

			}
, 			{
				"box" : 				{
					"args" : [ "panner~" ],
					"id" : "obj-1",
					"maxclass" : "bpatcher",
					"name" : "omod.panner~.maxpat",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 180.0, 80.0, 140.0, 210.0 ]
				}

			}
, 			{
				"box" : 				{
					"bgcolor" : [ 0.996078, 0.976471, 1.0, 0.0 ],
					"border" : 2,
					"bordercolor" : [ 0.502292, 0.000097, 0.263935, 1.0 ],
					"id" : "obj-4",
					"maxclass" : "panel",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 170.0, 70.0, 160.0, 230.0 ]
				}

			}
 ],
		"lines" : [ 			{
				"patchline" : 				{
					"destination" : [ "obj-26", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-1", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-12", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-10", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-12", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-11", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-12", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-13", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-12", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-14", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-12", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-15", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-12", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-16", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-12", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-19", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-25", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-2", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-12", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-20", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-12", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-27", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-17", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-3", 0 ]
				}

			}
 ],
		"parameters" : 		{
			"obj-3::obj-13::obj-36" : [ "live.numbox[72]", "live.numbox[1]", 0 ],
			"obj-2::obj-13::obj-60" : [ "live.numbox[13]", "live.numbox", 0 ],
			"obj-1::obj-13::obj-38" : [ "live.numbox[17]", "live.numbox[4]", 0 ],
			"obj-1::obj-13::obj-2::obj-60" : [ "live.numbox[1]", "live.numbox", 0 ],
			"obj-2::obj-13::obj-54" : [ "live.numbox[14]", "live.numbox", 0 ],
			"obj-1::obj-13::obj-2::obj-54" : [ "live.numbox", "live.numbox", 0 ],
			"obj-2::obj-13::obj-56::obj-33" : [ "live.tab[6]", "live.tab[1]", 0 ],
			"obj-3::obj-13::obj-204" : [ "live.numbox[73]", "live.numbox", 0 ],
			"obj-2::obj-13::obj-56::obj-42" : [ "live.numbox[28]", "live.numbox", 0 ],
			"obj-2::obj-13::obj-62::obj-36" : [ "live.numbox[2]", "live.numbox[1]", 0 ],
			"obj-2::obj-13::obj-56::obj-41" : [ "live.numbox[29]", "live.numbox", 0 ],
			"obj-2::obj-13::obj-62::obj-51" : [ "live.numbox[4]", "live.numbox", 0 ],
			"obj-2::obj-13::obj-58::obj-21" : [ "live.numbox[3]", "live.numbox", 0 ]
		}
,
		"dependency_cache" : [ 			{
				"name" : "omod.panner~.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.panner~",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.panner~.model.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.panner~",
				"patcherrelativepath" : ".",
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
				"name" : "omod.panner~.voix.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.panner~",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.dsp.panner~.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/abstractions/dsp",
				"patcherrelativepath" : "../../abstractions/dsp",
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
				"name" : "oovu.bundle.dsp.poly~.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/abstractions/bundles",
				"patcherrelativepath" : "../../abstractions/bundles",
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
				"name" : "oovu.bundle.dsp.base.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/abstractions/bundles",
				"patcherrelativepath" : "../../abstractions/bundles",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.panner~.view.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.panner~",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.guibackground.js",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/jsui",
				"patcherrelativepath" : "../../../jsui",
				"type" : "TEXT",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.gui.commonio.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/abstractions/gui",
				"patcherrelativepath" : "../../abstractions/gui",
				"type" : "JSON",
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
				"name" : "omod.output~.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.output~",
				"patcherrelativepath" : "../omod.output~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.output~.model.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.output~",
				"patcherrelativepath" : "../omod.output~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.output~.voix.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.output~",
				"patcherrelativepath" : "../omod.output~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.output~.view.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.output~",
				"patcherrelativepath" : "../omod.output~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
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
				"name" : "omod.input~.voix.maxpat",
				"bootpath" : "/Applications/Max 6.1/packages/oovu/patchers/modules/omod.input~",
				"patcherrelativepath" : "../omod.input~",
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

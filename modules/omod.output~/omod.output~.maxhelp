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
		"rect" : [ 16.0, 389.0, 835.0, 328.0 ],
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
					"args" : [ "allpasser~" ],
					"id" : "obj-14",
					"maxclass" : "bpatcher",
					"name" : "omod.allpass~.maxpat",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 560.0, 65.0, 240.0, 210.0 ]
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
					"patching_rect" : [ 310.0, 40.0, 78.0, 18.0 ],
					"text" : ":show view"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-12",
					"maxclass" : "newobj",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 310.0, 290.0, 96.0, 20.0 ],
					"text" : "print /allpasser~"
				}

			}
, 			{
				"box" : 				{
					"args" : [ "allpasser~" ],
					"id" : "obj-10",
					"maxclass" : "bpatcher",
					"name" : "omod.allpass~.maxpat",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 310.0, 65.0, 240.0, 210.0 ]
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-9",
					"maxclass" : "message",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 10.0, 10.0, 78.0, 18.0 ],
					"text" : ":show model"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-4",
					"maxclass" : "message",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 160.0, 40.0, 78.0, 18.0 ],
					"text" : ":show view"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-8",
					"maxclass" : "message",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 25.0, 35.0, 78.0, 18.0 ],
					"text" : ":show view"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-6",
					"maxclass" : "newobj",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 10.0, 285.0, 64.0, 20.0 ],
					"text" : "print /in~"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-5",
					"maxclass" : "newobj",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 160.0, 280.0, 64.0, 20.0 ],
					"text" : "print /out~"
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
					"patching_rect" : [ 160.0, 65.0, 140.0, 210.0 ]
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
					"patching_rect" : [ 10.0, 65.0, 140.0, 210.0 ]
				}

			}
 ],
		"lines" : [ 			{
				"patchline" : 				{
					"destination" : [ "obj-6", 0 ],
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
					"destination" : [ "obj-10", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-13", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-5", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-2", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-2", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-4", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-1", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-8", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-1", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-9", 0 ]
				}

			}
 ],
		"parameters" : 		{
			"obj-14::obj-13::obj-12" : [ "live.slider[9]", "live.slider", 0 ],
			"obj-14::obj-13::obj-204" : [ "live.numbox[24]", "live.numbox", 0 ],
			"obj-1::obj-13::obj-11::obj-18::obj-4" : [ "live.slider", "live.slider", 0 ],
			"obj-1::obj-13::obj-58::obj-21" : [ "live.numbox[2]", "live.numbox", 0 ],
			"obj-10::obj-13::obj-204" : [ "live.numbox[14]", "live.numbox", 0 ],
			"obj-1::obj-13::obj-56::obj-33" : [ "live.tab[3]", "live.tab[1]", 0 ],
			"obj-1::obj-13::obj-62::obj-51" : [ "live.numbox[4]", "live.numbox", 0 ],
			"obj-14::obj-13::obj-18::obj-204" : [ "live.numbox[23]", "live.numbox", 0 ],
			"obj-14::obj-13::obj-49" : [ "live.numbox[16]", "live.numbox[4]", 0 ],
			"obj-14::obj-13::obj-40" : [ "live.numbox[17]", "live.numbox[4]", 0 ],
			"obj-1::obj-13::obj-6" : [ "live.slider[3]", "live.slider", 0 ],
			"obj-10::obj-13::obj-18::obj-18::obj-4" : [ "live.slider[5]", "live.slider", 0 ],
			"obj-14::obj-13::obj-21" : [ "live.numbox[22]", "live.numbox[4]", 0 ],
			"obj-2::obj-13::obj-204" : [ "live.numbox[15]", "live.numbox", 0 ],
			"obj-14::obj-13::obj-36" : [ "live.numbox[20]", "live.numbox[4]", 0 ],
			"obj-1::obj-13::obj-56::obj-41" : [ "live.numbox", "live.numbox", 0 ],
			"obj-1::obj-13::obj-9" : [ "live.tab", "live.tab", 0 ],
			"obj-10::obj-13::obj-36" : [ "live.numbox[7]", "live.numbox[4]", 0 ],
			"obj-10::obj-13::obj-40" : [ "live.numbox[9]", "live.numbox[4]", 0 ],
			"obj-10::obj-13::obj-21" : [ "live.numbox[12]", "live.numbox[4]", 0 ],
			"obj-10::obj-13::obj-18::obj-204" : [ "live.numbox[13]", "live.numbox", 0 ],
			"obj-10::obj-13::obj-49" : [ "live.numbox[10]", "live.numbox[4]", 0 ],
			"obj-14::obj-13::obj-31" : [ "live.numbox[19]", "live.numbox[4]", 0 ],
			"obj-14::obj-13::obj-6" : [ "live.slider[10]", "live.slider", 0 ],
			"obj-1::obj-13::obj-56::obj-42" : [ "live.numbox[1]", "live.numbox", 0 ],
			"obj-1::obj-13::obj-11::obj-204" : [ "live.numbox[3]", "live.numbox", 0 ],
			"obj-10::obj-13::obj-6" : [ "live.slider[6]", "live.slider", 0 ],
			"obj-10::obj-13::obj-38" : [ "live.numbox[8]", "live.numbox[4]", 0 ],
			"obj-10::obj-13::obj-26" : [ "live.numbox[11]", "live.numbox[4]", 0 ],
			"obj-14::obj-13::obj-26" : [ "live.numbox[21]", "live.numbox[4]", 0 ],
			"obj-14::obj-13::obj-38" : [ "live.numbox[18]", "live.numbox[4]", 0 ],
			"obj-2::obj-13::obj-6" : [ "live.slider[7]", "live.slider", 0 ],
			"obj-10::obj-13::obj-31" : [ "live.numbox[6]", "live.numbox[4]", 0 ],
			"obj-10::obj-13::obj-12" : [ "live.slider[1]", "live.slider", 0 ],
			"obj-14::obj-13::obj-18::obj-18::obj-4" : [ "live.slider[8]", "live.slider", 0 ]
		}
,
		"dependency_cache" : [ 			{
				"name" : "omod.input~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.input~",
				"patcherrelativepath" : "../omod.input~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.input~.model.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.input~",
				"patcherrelativepath" : "../omod.input~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.bundle.dsp.sender.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/bundles",
				"patcherrelativepath" : "../../abstractions/bundles",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.property.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/core",
				"patcherrelativepath" : "../../abstractions/core",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.obj.argssetup.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/internals",
				"patcherrelativepath" : "../../abstractions/internals",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.obj.attribute.dispatch.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/internals",
				"patcherrelativepath" : "../../abstractions/internals",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.obj.route.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/internals",
				"patcherrelativepath" : "../../abstractions/internals",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.re.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "../../abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.messaging.call.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/messaging",
				"patcherrelativepath" : "../../abstractions/messaging",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.queue.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "../../abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.messaging.reply.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/messaging",
				"patcherrelativepath" : "../../abstractions/messaging",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.obj.get.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/internals",
				"patcherrelativepath" : "../../abstractions/internals",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.silentdict.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "../../abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.datatype.integer.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/datatypes",
				"patcherrelativepath" : "../../abstractions/datatypes",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.clip.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "../../abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.interface.extremum.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/interfaces",
				"patcherrelativepath" : "../../abstractions/interfaces",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.obj.this.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/internals",
				"patcherrelativepath" : "../../abstractions/internals",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.rv.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "../../abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.map.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "../../abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.obj.attribute.constructor.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/internals",
				"patcherrelativepath" : "../../abstractions/internals",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.environment.js",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/core",
				"patcherrelativepath" : "../../abstractions/core",
				"type" : "TEXT",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.interface.attribute.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/interfaces",
				"patcherrelativepath" : "../../abstractions/interfaces",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.interface.common.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/interfaces",
				"patcherrelativepath" : "../../abstractions/interfaces",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.obj.property.dispatch.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/internals",
				"patcherrelativepath" : "../../abstractions/internals",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.interface.value.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/interfaces",
				"patcherrelativepath" : "../../abstractions/interfaces",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.source.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/pushpull",
				"patcherrelativepath" : "../../abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.source.poly~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/pushpull",
				"patcherrelativepath" : "../../abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.lazyproperty.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/core",
				"patcherrelativepath" : "../../abstractions/core",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.dictsub.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "../../abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.datatype.decimal.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/datatypes",
				"patcherrelativepath" : "../../abstractions/datatypes",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.datatype.push.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/datatypes",
				"patcherrelativepath" : "../../abstractions/datatypes",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.updated.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/core",
				"patcherrelativepath" : "../../abstractions/core",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.interface.push.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/interfaces",
				"patcherrelativepath" : "../../abstractions/interfaces",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.source.dispatch.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/pushpull",
				"patcherrelativepath" : "../../abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.binding.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/core",
				"patcherrelativepath" : "../../abstractions/core",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.dsp.8chlevels.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/dsp",
				"patcherrelativepath" : "../../abstractions/dsp",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.input~.voix.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.input~",
				"patcherrelativepath" : "../omod.input~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.dsp.thispoly~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/dsp",
				"patcherrelativepath" : "../../abstractions/dsp",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.datatype.option.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/datatypes",
				"patcherrelativepath" : "../../abstractions/datatypes",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.interface.option.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/interfaces",
				"patcherrelativepath" : "../../abstractions/interfaces",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.datatype.filesystempath.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/datatypes",
				"patcherrelativepath" : "../../abstractions/datatypes",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.datatype.boolean.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/datatypes",
				"patcherrelativepath" : "../../abstractions/datatypes",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.interface.boolean.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/interfaces",
				"patcherrelativepath" : "../../abstractions/interfaces",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.method.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/core",
				"patcherrelativepath" : "../../abstractions/core",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.obj.show.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/internals",
				"patcherrelativepath" : "../../abstractions/internals",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.module.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/core",
				"patcherrelativepath" : "../../abstractions/core",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.obj.module.dispatch.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/internals",
				"patcherrelativepath" : "../../abstractions/internals",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.obj.module.constructor.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/internals",
				"patcherrelativepath" : "../../abstractions/internals",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.obj.randdelay.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/internals",
				"patcherrelativepath" : "../../abstractions/internals",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.interface.module.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/interfaces",
				"patcherrelativepath" : "../../abstractions/interfaces",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.input~.view.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.input~",
				"patcherrelativepath" : "../omod.input~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.guibackground.js",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/gui",
				"patcherrelativepath" : "../../abstractions/gui",
				"type" : "TEXT",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.gui.meters.8x.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/gui",
				"patcherrelativepath" : "../../abstractions/gui",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.input~.view.oscillator.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.input~",
				"patcherrelativepath" : "../omod.input~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "sine.svg",
				"bootpath" : "/Applications/Max 6.1/patches/picts/m4l-picts",
				"patcherrelativepath" : "../../../../../../../../Applications/Max 6.1/patches/picts/m4l-picts",
				"type" : "svg ",
				"implicit" : 1
			}
, 			{
				"name" : "saw.svg",
				"bootpath" : "/Applications/Max 6.1/patches/picts/m4l-picts",
				"patcherrelativepath" : "../../../../../../../../Applications/Max 6.1/patches/picts/m4l-picts",
				"type" : "svg ",
				"implicit" : 1
			}
, 			{
				"name" : "square.svg",
				"bootpath" : "/Applications/Max 6.1/patches/picts/m4l-picts",
				"patcherrelativepath" : "../../../../../../../../Applications/Max 6.1/patches/picts/m4l-picts",
				"type" : "svg ",
				"implicit" : 1
			}
, 			{
				"name" : "random.svg",
				"bootpath" : "/Applications/Max 6.1/patches/picts/m4l-picts",
				"patcherrelativepath" : "../../../../../../../../Applications/Max 6.1/patches/picts/m4l-picts",
				"type" : "svg ",
				"implicit" : 1
			}
, 			{
				"name" : "omod.input~.view.soundfile.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.input~",
				"patcherrelativepath" : "../omod.input~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.input~.view.adc.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.input~",
				"patcherrelativepath" : "../omod.input~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.gui.send.dynamic.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/gui",
				"patcherrelativepath" : "../../abstractions/gui",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.gui.send.1x.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/gui",
				"patcherrelativepath" : "../../abstractions/gui",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.makeumenu.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "../../abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.output~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.output~",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.output~.model.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.output~",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.bundle.dsp.receiver.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/bundles",
				"patcherrelativepath" : "../../abstractions/bundles",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.destination.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/pushpull",
				"patcherrelativepath" : "../../abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.destination.dispatch.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/pushpull",
				"patcherrelativepath" : "../../abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.obj.audionode.constructor.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/internals",
				"patcherrelativepath" : "../../abstractions/internals",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.destination.poly~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/pushpull",
				"patcherrelativepath" : "../../abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.output~.voix.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.output~",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.datatype.intarray.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/datatypes",
				"patcherrelativepath" : "../../abstractions/datatypes",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.interface.array.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/interfaces",
				"patcherrelativepath" : "../../abstractions/interfaces",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.map1.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "../../abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.fixedarray.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "../../abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.bundle.dsp.base.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/bundles",
				"patcherrelativepath" : "../../abstractions/bundles",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.output~.view.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.output~",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.allpass~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.allpass~",
				"patcherrelativepath" : "../omod.allpass~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.allpass~.model.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.allpass~",
				"patcherrelativepath" : "../omod.allpass~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.datatype.range.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/datatypes",
				"patcherrelativepath" : "../../abstractions/datatypes",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.range2centerwidth.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "../../abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.lineseg.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "../../abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.centerwidth2range.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "../../abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.interface.range.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/interfaces",
				"patcherrelativepath" : "../../abstractions/interfaces",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.allpass~.voix.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.allpass~",
				"patcherrelativepath" : "../omod.allpass~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.allpass~.voix.kernelwrapper.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.allpass~",
				"patcherrelativepath" : "../omod.allpass~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.allpass~.voix.kernel.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.allpass~",
				"patcherrelativepath" : "../omod.allpass~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.dsp.poly.1x1.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/dsp",
				"patcherrelativepath" : "../../abstractions/dsp",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.bundle.dsp.poly~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/bundles",
				"patcherrelativepath" : "../../abstractions/bundles",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.allpass~.view.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.allpass~",
				"patcherrelativepath" : "../omod.allpass~",
				"type" : "JSON",
				"implicit" : 1
			}
 ]
	}

}

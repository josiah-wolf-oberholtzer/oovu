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
		"rect" : [ 246.0, 44.0, 619.0, 730.0 ],
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
					"args" : [ "in~.4" ],
					"id" : "obj-3",
					"maxclass" : "bpatcher",
					"name" : "omod.input~.maxpat",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 460.0, 10.0, 140.0, 210.0 ]
				}

			}
, 			{
				"box" : 				{
					"args" : [ "in~.3" ],
					"id" : "obj-2",
					"maxclass" : "bpatcher",
					"name" : "omod.input~.maxpat",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 310.0, 10.0, 140.0, 210.0 ]
				}

			}
, 			{
				"box" : 				{
					"args" : [ "in~.2" ],
					"id" : "obj-26",
					"maxclass" : "bpatcher",
					"name" : "omod.input~.maxpat",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 160.0, 10.0, 140.0, 210.0 ]
				}

			}
, 			{
				"box" : 				{
					"args" : [ "in~.1" ],
					"id" : "obj-25",
					"maxclass" : "bpatcher",
					"name" : "omod.input~.maxpat",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 10.0, 10.0, 140.0, 210.0 ]
				}

			}
, 			{
				"box" : 				{
					"args" : [ "delays~" ],
					"id" : "obj-17",
					"maxclass" : "bpatcher",
					"name" : "omod.allpass~.maxpat",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 310.0, 230.0, 240.0, 210.0 ]
				}

			}
, 			{
				"box" : 				{
					"args" : [ "reverb~" ],
					"id" : "obj-16",
					"maxclass" : "bpatcher",
					"name" : "omod.reverb~.maxpat",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 160.0, 230.0, 140.0, 210.0 ]
				}

			}
, 			{
				"box" : 				{
					"args" : [ "cues" ],
					"id" : "obj-14",
					"maxclass" : "bpatcher",
					"name" : "omod.cuescript.maxpat",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 160.0, 450.0, 140.0, 210.0 ]
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
					"patching_rect" : [ 10.0, 670.0, 67.0, 20.0 ],
					"text" : "print /out~"
				}

			}
, 			{
				"box" : 				{
					"args" : [ "out~" ],
					"id" : "obj-1",
					"maxclass" : "bpatcher",
					"name" : "omod.output~.maxpat",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 10.0, 450.0, 140.0, 210.0 ]
				}

			}
 ],
		"lines" : [ 			{
				"patchline" : 				{
					"destination" : [ "obj-12", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-1", 0 ]
				}

			}
 ],
		"parameters" : 		{
			"obj-25::obj-13::obj-56::obj-42" : [ "live.numbox[28]", "live.numbox", 0 ],
			"obj-25::obj-13::obj-56::obj-41" : [ "live.numbox[29]", "live.numbox", 0 ],
			"obj-3::obj-13::obj-11::obj-204" : [ "live.numbox[21]", "live.numbox", 0 ],
			"obj-16::obj-13::obj-33" : [ "live.numbox[36]", "live.numbox[4]", 0 ],
			"obj-3::obj-13::obj-58::obj-21" : [ "live.numbox[52]", "live.numbox", 0 ],
			"obj-17::obj-13::obj-36" : [ "live.numbox[43]", "live.numbox[4]", 0 ],
			"obj-17::obj-13::obj-31" : [ "live.numbox[42]", "live.numbox[4]", 0 ],
			"obj-26::obj-13::obj-62::obj-51" : [ "live.numbox[6]", "live.numbox", 0 ],
			"obj-26::obj-13::obj-11::obj-204" : [ "live.numbox[16]", "live.numbox", 0 ],
			"obj-1::obj-13::obj-36" : [ "live.numbox[1]", "live.numbox[1]", 0 ],
			"obj-26::obj-13::obj-56::obj-41" : [ "live.numbox[9]", "live.numbox", 0 ],
			"obj-2::obj-13::obj-56::obj-42" : [ "live.numbox[19]", "live.numbox", 0 ],
			"obj-25::obj-13::obj-9" : [ "live.tab", "live.tab", 0 ],
			"obj-16::obj-13::obj-39" : [ "live.numbox[35]", "live.numbox[4]", 0 ],
			"obj-16::obj-13::obj-6" : [ "live.slider[12]", "live.slider", 0 ],
			"obj-3::obj-13::obj-11::obj-18::obj-4" : [ "live.slider[4]", "live.slider", 0 ],
			"obj-17::obj-13::obj-204" : [ "live.numbox[46]", "live.numbox", 0 ],
			"obj-17::obj-13::obj-26" : [ "live.numbox[44]", "live.numbox[4]", 0 ],
			"obj-25::obj-13::obj-11::obj-18::obj-4" : [ "live.slider[16]", "live.slider", 0 ],
			"obj-26::obj-13::obj-6" : [ "live.slider[19]", "live.slider", 0 ],
			"obj-2::obj-13::obj-62::obj-51" : [ "live.numbox[49]", "live.numbox", 0 ],
			"obj-25::obj-13::obj-62::obj-51" : [ "live.numbox[5]", "live.numbox", 0 ],
			"obj-2::obj-13::obj-6" : [ "live.slider[21]", "live.slider", 0 ],
			"obj-16::obj-13::obj-12" : [ "live.slider[1]", "live.slider", 0 ],
			"obj-26::obj-13::obj-56::obj-42" : [ "live.numbox[8]", "live.numbox", 0 ],
			"obj-3::obj-13::obj-62::obj-51" : [ "live.numbox[51]", "live.numbox", 0 ],
			"obj-26::obj-13::obj-58::obj-21" : [ "live.numbox[7]", "live.numbox", 0 ],
			"obj-2::obj-13::obj-58::obj-21" : [ "live.numbox[18]", "live.numbox", 0 ],
			"obj-16::obj-13::obj-18::obj-18::obj-4" : [ "live.slider[11]", "live.slider", 0 ],
			"obj-2::obj-13::obj-56::obj-41" : [ "live.numbox[20]", "live.numbox", 0 ],
			"obj-2::obj-13::obj-9" : [ "live.tab[4]", "live.tab", 0 ],
			"obj-17::obj-13::obj-18::obj-204" : [ "live.numbox[45]", "live.numbox", 0 ],
			"obj-3::obj-13::obj-56::obj-42" : [ "live.numbox[53]", "live.numbox", 0 ],
			"obj-17::obj-13::obj-12" : [ "live.slider[14]", "live.slider", 0 ],
			"obj-25::obj-13::obj-56::obj-33" : [ "live.tab[6]", "live.tab[1]", 0 ],
			"obj-25::obj-13::obj-62::obj-36" : [ "live.numbox[4]", "live.numbox[1]", 0 ],
			"obj-17::obj-13::obj-40" : [ "live.numbox[40]", "live.numbox[4]", 0 ],
			"obj-16::obj-13::obj-204" : [ "live.numbox[14]", "live.numbox", 0 ],
			"obj-17::obj-13::obj-21" : [ "live.numbox[12]", "live.numbox[4]", 0 ],
			"obj-2::obj-13::obj-62::obj-36" : [ "live.numbox[48]", "live.numbox[1]", 0 ],
			"obj-1::obj-13::obj-204" : [ "live.numbox[15]", "live.numbox", 0 ],
			"obj-2::obj-13::obj-11::obj-18::obj-4" : [ "live.slider[20]", "live.slider", 0 ],
			"obj-16::obj-13::obj-38" : [ "live.numbox[37]", "live.numbox[4]", 0 ],
			"obj-25::obj-13::obj-6" : [ "live.slider[3]", "live.slider", 0 ],
			"obj-2::obj-13::obj-56::obj-33" : [ "live.tab[3]", "live.tab[1]", 0 ],
			"obj-1::obj-13::obj-6" : [ "live.slider[7]", "live.slider", 0 ],
			"obj-17::obj-13::obj-49" : [ "live.numbox[39]", "live.numbox[4]", 0 ],
			"obj-17::obj-13::obj-6" : [ "live.slider[15]", "live.slider", 0 ],
			"obj-26::obj-13::obj-56::obj-33" : [ "live.tab[1]", "live.tab[1]", 0 ],
			"obj-25::obj-13::obj-58::obj-21" : [ "live.numbox[2]", "live.numbox", 0 ],
			"obj-26::obj-13::obj-62::obj-36" : [ "live.numbox[17]", "live.numbox[1]", 0 ],
			"obj-2::obj-13::obj-11::obj-204" : [ "live.numbox[47]", "live.numbox", 0 ],
			"obj-17::obj-13::obj-18::obj-18::obj-4" : [ "live.slider", "live.slider", 0 ],
			"obj-16::obj-13::obj-18::obj-204" : [ "live.numbox[38]", "live.numbox", 0 ],
			"obj-16::obj-13::obj-41" : [ "live.numbox[34]", "live.numbox[4]", 0 ],
			"obj-3::obj-13::obj-6" : [ "live.slider[22]", "live.slider", 0 ],
			"obj-26::obj-13::obj-9" : [ "live.tab[2]", "live.tab", 0 ],
			"obj-3::obj-13::obj-56::obj-41" : [ "live.numbox[54]", "live.numbox", 0 ],
			"obj-17::obj-13::obj-38" : [ "live.numbox[41]", "live.numbox[4]", 0 ],
			"obj-25::obj-13::obj-11::obj-204" : [ "live.numbox[3]", "live.numbox", 0 ],
			"obj-3::obj-13::obj-56::obj-33" : [ "live.tab[5]", "live.tab[1]", 0 ],
			"obj-3::obj-13::obj-9" : [ "live.tab[7]", "live.tab", 0 ],
			"obj-26::obj-13::obj-11::obj-18::obj-4" : [ "live.slider[18]", "live.slider", 0 ],
			"obj-3::obj-13::obj-62::obj-36" : [ "live.numbox[50]", "live.numbox[1]", 0 ]
		}
,
		"dependency_cache" : [ 			{
				"name" : "omod.output~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.output~",
				"patcherrelativepath" : "./modules/omod.output~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.output~.model.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.output~",
				"patcherrelativepath" : "./modules/omod.output~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.bundle.dsp.receiver.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/bundles",
				"patcherrelativepath" : "./abstractions/bundles",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.destination.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/pushpull",
				"patcherrelativepath" : "./abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.destination.dispatch.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/pushpull",
				"patcherrelativepath" : "./abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.obj.audionode.constructor.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/internals",
				"patcherrelativepath" : "./abstractions/internals",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.obj.argssetup.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/internals",
				"patcherrelativepath" : "./abstractions/internals",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.environment.js",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/core",
				"patcherrelativepath" : "./abstractions/core",
				"type" : "TEXT",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.destination.poly~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/pushpull",
				"patcherrelativepath" : "./abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "mxj oovu.Binding.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/core",
				"patcherrelativepath" : "./abstractions/core",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.messaging.call.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/messaging",
				"patcherrelativepath" : "./abstractions/messaging",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.queue.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "./abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.updated.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/core",
				"patcherrelativepath" : "./abstractions/core",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.dsp.8chlevels.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/dsp",
				"patcherrelativepath" : "./abstractions/dsp",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "mxj oovu.Property.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/core",
				"patcherrelativepath" : "./abstractions/core",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.obj.attribute.dispatch.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/internals",
				"patcherrelativepath" : "./abstractions/internals",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.obj.route.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/internals",
				"patcherrelativepath" : "./abstractions/internals",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.re.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "./abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.messaging.reply.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/messaging",
				"patcherrelativepath" : "./abstractions/messaging",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.obj.get.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/internals",
				"patcherrelativepath" : "./abstractions/internals",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.silentdict.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "./abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.datatype.decimal.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/datatypes",
				"patcherrelativepath" : "./abstractions/datatypes",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.clip.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "./abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.interface.extremum.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/interfaces",
				"patcherrelativepath" : "./abstractions/interfaces",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.obj.this.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/internals",
				"patcherrelativepath" : "./abstractions/internals",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.rv.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "./abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.map.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "./abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.obj.attribute.constructor.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/internals",
				"patcherrelativepath" : "./abstractions/internals",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.interface.attribute.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/interfaces",
				"patcherrelativepath" : "./abstractions/interfaces",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.interface.common.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/interfaces",
				"patcherrelativepath" : "./abstractions/interfaces",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.obj.property.dispatch.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/internals",
				"patcherrelativepath" : "./abstractions/internals",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.interface.value.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/interfaces",
				"patcherrelativepath" : "./abstractions/interfaces",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.output~.voix.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.output~",
				"patcherrelativepath" : "./modules/omod.output~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "mxj oovu.Method.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/core",
				"patcherrelativepath" : "./abstractions/core",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.datatype.filesystempath.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/datatypes",
				"patcherrelativepath" : "./abstractions/datatypes",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.datatype.boolean.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/datatypes",
				"patcherrelativepath" : "./abstractions/datatypes",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.interface.boolean.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/interfaces",
				"patcherrelativepath" : "./abstractions/interfaces",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.datatype.intarray.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/datatypes",
				"patcherrelativepath" : "./abstractions/datatypes",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.interface.array.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/interfaces",
				"patcherrelativepath" : "./abstractions/interfaces",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.map1.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "./abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.fixedarray.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "./abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.bundle.dsp.base.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/bundles",
				"patcherrelativepath" : "./abstractions/bundles",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.datatype.integer.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/datatypes",
				"patcherrelativepath" : "./abstractions/datatypes",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.obj.show.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/internals",
				"patcherrelativepath" : "./abstractions/internals",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "mxj oovu.Module.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/core",
				"patcherrelativepath" : "./abstractions/core",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.obj.module.dispatch.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/internals",
				"patcherrelativepath" : "./abstractions/internals",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.obj.module.constructor.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/internals",
				"patcherrelativepath" : "./abstractions/internals",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.obj.randdelay.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/internals",
				"patcherrelativepath" : "./abstractions/internals",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.interface.module.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/interfaces",
				"patcherrelativepath" : "./abstractions/interfaces",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.output~.view.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.output~",
				"patcherrelativepath" : "./modules/omod.output~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.guibackground.js",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/gui",
				"patcherrelativepath" : "./abstractions/gui",
				"type" : "TEXT",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.gui.meters.8x.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/gui",
				"patcherrelativepath" : "./abstractions/gui",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.cuescript.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.cuescript",
				"patcherrelativepath" : "./modules/omod.cuescript",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.cuescript.model.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.cuescript",
				"patcherrelativepath" : "./modules/omod.cuescript",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.datatype.option.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/datatypes",
				"patcherrelativepath" : "./abstractions/datatypes",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.interface.option.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/interfaces",
				"patcherrelativepath" : "./abstractions/interfaces",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.datatype.generic.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/datatypes",
				"patcherrelativepath" : "./abstractions/datatypes",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.cuescript.kernel.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.cuescript",
				"patcherrelativepath" : "./modules/omod.cuescript",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.getstate.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/core",
				"patcherrelativepath" : "./abstractions/core",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.send.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/core",
				"patcherrelativepath" : "./abstractions/core",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.cuescript.view.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.cuescript",
				"patcherrelativepath" : "./modules/omod.cuescript",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.makeumenu.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "./abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.reverb~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.reverb~",
				"patcherrelativepath" : "./modules/omod.reverb~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.reverb~.model.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.reverb~",
				"patcherrelativepath" : "./modules/omod.reverb~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.bundle.dsp.sender.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/bundles",
				"patcherrelativepath" : "./abstractions/bundles",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.source.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/pushpull",
				"patcherrelativepath" : "./abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.source.poly~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/pushpull",
				"patcherrelativepath" : "./abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.lazyproperty.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/core",
				"patcherrelativepath" : "./abstractions/core",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.dictsub.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "./abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.datatype.push.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/datatypes",
				"patcherrelativepath" : "./abstractions/datatypes",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.interface.push.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/interfaces",
				"patcherrelativepath" : "./abstractions/interfaces",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.push.source.dispatch.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/pushpull",
				"patcherrelativepath" : "./abstractions/pushpull",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.reverb~.voix.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.reverb~",
				"patcherrelativepath" : "./modules/omod.reverb~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.reverb~.voix.kernel.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.reverb~",
				"patcherrelativepath" : "./modules/omod.reverb~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "freeverb.gendsp",
				"bootpath" : "/Applications/Max 6.1/examples/gen",
				"patcherrelativepath" : "../../../../../../Applications/Max 6.1/examples/gen",
				"type" : "gDSP",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.dsp.poly.1x1.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/dsp",
				"patcherrelativepath" : "./abstractions/dsp",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.bundle.dsp.poly~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/bundles",
				"patcherrelativepath" : "./abstractions/bundles",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.reverb~.view.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.reverb~",
				"patcherrelativepath" : "./modules/omod.reverb~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.gui.send.dynamic.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/gui",
				"patcherrelativepath" : "./abstractions/gui",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.gui.send.1x.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/gui",
				"patcherrelativepath" : "./abstractions/gui",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.allpass~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.allpass~",
				"patcherrelativepath" : "./modules/omod.allpass~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.allpass~.model.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.allpass~",
				"patcherrelativepath" : "./modules/omod.allpass~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.datatype.range.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/datatypes",
				"patcherrelativepath" : "./abstractions/datatypes",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.range2centerwidth.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "./abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.lineseg.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "./abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.util.centerwidth2range.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "./abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.interface.range.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/interfaces",
				"patcherrelativepath" : "./abstractions/interfaces",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.allpass~.voix.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.allpass~",
				"patcherrelativepath" : "./modules/omod.allpass~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.allpass~.voix.kernelwrapper.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.allpass~",
				"patcherrelativepath" : "./modules/omod.allpass~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.allpass~.voix.kernel.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.allpass~",
				"patcherrelativepath" : "./modules/omod.allpass~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.allpass~.view.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.allpass~",
				"patcherrelativepath" : "./modules/omod.allpass~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.input~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.input~",
				"patcherrelativepath" : "./modules/omod.input~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.input~.model.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.input~",
				"patcherrelativepath" : "./modules/omod.input~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.input~.voix.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.input~",
				"patcherrelativepath" : "./modules/omod.input~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.dsp.thispoly~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/dsp",
				"patcherrelativepath" : "./abstractions/dsp",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.input~.view.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.input~",
				"patcherrelativepath" : "./modules/omod.input~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.input~.view.oscillator.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.input~",
				"patcherrelativepath" : "./modules/omod.input~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "sine.svg",
				"bootpath" : "/Applications/Max 6.1/patches/picts/m4l-picts",
				"patcherrelativepath" : "../../../../../../Applications/Max 6.1/patches/picts/m4l-picts",
				"type" : "svg ",
				"implicit" : 1
			}
, 			{
				"name" : "saw.svg",
				"bootpath" : "/Applications/Max 6.1/patches/picts/m4l-picts",
				"patcherrelativepath" : "../../../../../../Applications/Max 6.1/patches/picts/m4l-picts",
				"type" : "svg ",
				"implicit" : 1
			}
, 			{
				"name" : "square.svg",
				"bootpath" : "/Applications/Max 6.1/patches/picts/m4l-picts",
				"patcherrelativepath" : "../../../../../../Applications/Max 6.1/patches/picts/m4l-picts",
				"type" : "svg ",
				"implicit" : 1
			}
, 			{
				"name" : "random.svg",
				"bootpath" : "/Applications/Max 6.1/patches/picts/m4l-picts",
				"patcherrelativepath" : "../../../../../../Applications/Max 6.1/patches/picts/m4l-picts",
				"type" : "svg ",
				"implicit" : 1
			}
, 			{
				"name" : "omod.input~.view.soundfile.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.input~",
				"patcherrelativepath" : "./modules/omod.input~",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.input~.view.adc.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.input~",
				"patcherrelativepath" : "./modules/omod.input~",
				"type" : "JSON",
				"implicit" : 1
			}
 ]
	}

}

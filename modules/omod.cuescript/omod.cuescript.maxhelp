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
		"rect" : [ 541.0, 44.0, 312.0, 367.0 ],
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
					"id" : "obj-9",
					"maxclass" : "newobj",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 10.0, 295.0, 66.0, 20.0 ],
					"text" : "print /cues"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-6",
					"linecount" : 2,
					"maxclass" : "message",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 160.0, 75.0, 95.0, 31.0 ],
					"presentation_rect" : [ 162.0, 75.0, 0.0, 0.0 ],
					"text" : "/cue/:options foo bar baz"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-5",
					"maxclass" : "message",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 100.0, 15.0, 95.0, 18.0 ],
					"presentation_rect" : [ 232.0, 98.0, 0.0, 0.0 ],
					"text" : "/cue/:getoptions"
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
					"patching_rect" : [ 30.0, 40.0, 78.0, 18.0 ],
					"presentation_rect" : [ 160.0, 123.0, 0.0, 0.0 ],
					"text" : ":show view"
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
					"patching_rect" : [ 10.0, 10.0, 78.0, 18.0 ],
					"text" : ":show model"
				}

			}
, 			{
				"box" : 				{
					"args" : [ "cues" ],
					"id" : "obj-1",
					"maxclass" : "bpatcher",
					"name" : "omod.cuescript.maxpat",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 10.0, 75.0, 140.0, 210.0 ]
				}

			}
 ],
		"lines" : [ 			{
				"patchline" : 				{
					"destination" : [ "obj-9", 0 ],
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
					"source" : [ "obj-3", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-1", 0 ],
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
					"source" : [ "obj-5", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-1", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-6", 0 ]
				}

			}
 ],
		"dependency_cache" : [ 			{
				"name" : "omod.cuescript.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.cuescript",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "mxj oovu.Module.maxpat",
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
				"name" : "oovu.util.rv.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "../../abstractions/utilities",
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
				"name" : "oovu.obj.this.maxpat",
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
				"name" : "oovu.environment.js",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/core",
				"patcherrelativepath" : "../../abstractions/core",
				"type" : "TEXT",
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
				"name" : "mxj oovu.Binding.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/core",
				"patcherrelativepath" : "../../abstractions/core",
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
				"name" : "oovu.obj.argssetup.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/internals",
				"patcherrelativepath" : "../../abstractions/internals",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.cuescript.model.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.cuescript",
				"patcherrelativepath" : ".",
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
				"name" : "mxj oovu.Method.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/core",
				"patcherrelativepath" : "../../abstractions/core",
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
				"name" : "oovu.interface.attribute.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/interfaces",
				"patcherrelativepath" : "../../abstractions/interfaces",
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
				"name" : "oovu.datatype.generic.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/datatypes",
				"patcherrelativepath" : "../../abstractions/datatypes",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.cuescript.kernel.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.cuescript",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.getstate.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/core",
				"patcherrelativepath" : "../../abstractions/core",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "oovu.send.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/core",
				"patcherrelativepath" : "../../abstractions/core",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.cuescript.view.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.cuescript",
				"patcherrelativepath" : ".",
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
				"name" : "oovu.util.makeumenu.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/utilities",
				"patcherrelativepath" : "../../abstractions/utilities",
				"type" : "JSON",
				"implicit" : 1
			}
 ]
	}

}

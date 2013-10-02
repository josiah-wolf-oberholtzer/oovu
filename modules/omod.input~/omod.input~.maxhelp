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
		"rect" : [ 401.0, 44.0, 800.0, 684.0 ],
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
					"id" : "obj-35",
					"maxclass" : "button",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "bang" ],
					"patching_rect" : [ 35.0, 530.0, 20.0, 20.0 ]
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-33",
					"maxclass" : "newobj",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "bang" ],
					"patching_rect" : [ 35.0, 555.0, 100.0, 20.0 ],
					"text" : "oovu.updated"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-26",
					"maxclass" : "message",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 35.0, 445.0, 190.0, 18.0 ],
					"text" : "/send.1/destination/:show"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-18",
					"maxclass" : "newobj",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 360.0, 575.0, 225.0, 20.0 ],
					"text" : "oovu.push.destination.dispatch 9000 foo"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-23",
					"maxclass" : "newobj",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 360.0, 545.0, 132.0, 20.0 ],
					"text" : "mxj oovu.Module 9000 baz"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-22",
					"maxclass" : "message",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 25.0, 410.0, 190.0, 18.0 ],
					"text" : "/send.1/destination/:getaddresses"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-15",
					"items" : [ "((interface))", ",", "<separator>", ",", "---", ",", "dumpmeta", ",", "getid", ",", "getinterface", ",", "getmeta", ",", "getname", ",", "getproperties", ",", "show" ],
					"maxclass" : "umenu",
					"numinlets" : 1,
					"numoutlets" : 3,
					"outlettype" : [ "int", "", "" ],
					"parameter_enable" : 0,
					"patching_rect" : [ 490.0, 495.0, 100.0, 20.0 ]
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-16",
					"linecount" : 2,
					"maxclass" : "newobj",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 490.0, 450.0, 126.0, 33.0 ],
					"text" : "oovu.util.makeumenu interface"
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
					"patching_rect" : [ 270.0, 45.0, 81.0, 18.0 ],
					"text" : "getinterface"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-9",
					"items" : [ "((properties))", ",", "<separator>", ",", "---", ",", "adc/channel", ",", "dsp/active", ",", "oscillator/frequency", ",", "oscillator/pulserate", ",", "oscillator/pulsing", ",", "oscillator/waveform", ",", "output/gain", ",", "output/sendcount", ",", "send.1/amplitude", ",", "send.1/destination", ",", "send.2/amplitude", ",", "send.2/destination", ",", "send.3/amplitude", ",", "send.3/destination", ",", "soundfile/filepath", ",", "soundfile/looping", ",", "soundfile/playing", ",", "source" ],
					"maxclass" : "umenu",
					"numinlets" : 1,
					"numoutlets" : 3,
					"outlettype" : [ "int", "", "" ],
					"parameter_enable" : 0,
					"patching_rect" : [ 360.0, 495.0, 100.0, 20.0 ]
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-4",
					"linecount" : 2,
					"maxclass" : "newobj",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 360.0, 450.0, 126.0, 33.0 ],
					"text" : "oovu.util.makeumenu properties"
				}

			}
, 			{
				"box" : 				{
					"id" : "obj-25",
					"maxclass" : "toggle",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "int" ],
					"parameter_enable" : 0,
					"patching_rect" : [ 420.0, 325.0, 20.0, 20.0 ]
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-21",
					"maxclass" : "message",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 420.0, 350.0, 173.0, 18.0 ],
					"text" : "/soundfile/playing $1"
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
					"patching_rect" : [ 420.0, 270.0, 173.0, 18.0 ],
					"text" : "/source soundfile"
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
					"patching_rect" : [ 420.0, 230.0, 173.0, 18.0 ],
					"text" : "/soundfile/filepath cherokee.aif"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-17",
					"maxclass" : "message",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 110.0, 60.0, 50.0, 18.0 ],
					"text" : ":show"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-14",
					"maxclass" : "flonum",
					"numinlets" : 1,
					"numoutlets" : 2,
					"outlettype" : [ "float", "bang" ],
					"parameter_enable" : 0,
					"patching_rect" : [ 560.0, 120.0, 50.0, 20.0 ]
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-12",
					"maxclass" : "message",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 560.0, 145.0, 142.0, 18.0 ],
					"text" : "/oscillator/pulserate $1"
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
					"patching_rect" : [ 560.0, 90.0, 142.0, 18.0 ],
					"text" : "/oscillator/pulsing 1"
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
					"patching_rect" : [ 560.0, 30.0, 142.0, 18.0 ],
					"text" : "/oscillator/waveform sine"
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
					"patching_rect" : [ 180.0, 45.0, 81.0, 18.0 ],
					"text" : "getproperties"
				}

			}
, 			{
				"box" : 				{
					"id" : "obj-2",
					"maxclass" : "ezdac~",
					"numinlets" : 2,
					"numoutlets" : 0,
					"patching_rect" : [ 480.0, 110.0, 45.0, 45.0 ]
				}

			}
, 			{
				"box" : 				{
					"id" : "obj-7",
					"maxclass" : "toggle",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "int" ],
					"parameter_enable" : 0,
					"patching_rect" : [ 50.0, 30.0, 20.0, 20.0 ]
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-5",
					"linecount" : 2,
					"maxclass" : "message",
					"numinlets" : 2,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 50.0, 60.0, 50.0, 31.0 ],
					"text" : "/dsp/active $1"
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
					"patching_rect" : [ 55.0, 325.0, 217.0, 18.0 ],
					"text" : "/send.1/destination /baz/push/foo"
				}

			}
, 			{
				"box" : 				{
					"args" : [ "input~.1" ],
					"id" : "obj-1",
					"maxclass" : "bpatcher",
					"name" : "omod.input~.maxpat",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 50.0, 110.0, 190.0, 130.0 ]
				}

			}
 ],
		"lines" : [ 			{
				"patchline" : 				{
					"destination" : [ "obj-16", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-1", 1 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-3", 1 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-1", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-4", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-1", 1 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-1", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-10", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-1", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-11", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-1", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-12", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-1", 1 ],
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
					"destination" : [ "obj-15", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-16", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-1", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-17", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-1", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-19", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-1", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-20", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-1", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-21", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-1", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-22", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-21", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-25", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-1", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-26", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-33", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-35", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-9", 0 ],
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
					"destination" : [ "obj-5", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-7", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-1", 1 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-8", 0 ]
				}

			}
 ],
		"parameters" : 		{
			"obj-1::obj-13::obj-58::obj-21" : [ "live.numbox[2]", "live.numbox", 0 ],
			"obj-1::obj-13::obj-11::obj-204" : [ "live.numbox[3]", "live.numbox", 0 ],
			"obj-1::obj-13::obj-56::obj-33" : [ "live.tab[3]", "live.tab[1]", 0 ],
			"obj-1::obj-13::obj-11::obj-18::obj-4" : [ "live.slider", "live.slider", 0 ],
			"obj-1::obj-13::obj-6" : [ "live.slider[3]", "live.slider", 0 ],
			"obj-1::obj-13::obj-56::obj-41" : [ "live.numbox", "live.numbox", 0 ],
			"obj-1::obj-13::obj-62::obj-51" : [ "live.numbox[4]", "live.numbox", 0 ],
			"obj-1::obj-13::obj-9" : [ "live.tab", "live.tab", 0 ],
			"obj-1::obj-13::obj-56::obj-42" : [ "live.numbox[1]", "live.numbox", 0 ]
		}
,
		"dependency_cache" : [ 			{
				"name" : "omod.input~.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.input~",
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.input~.model.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.input~",
				"patcherrelativepath" : ".",
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
				"name" : "mxj oovu.Property.maxpat",
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
				"name" : "mxj oovu.Binding.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/core",
				"patcherrelativepath" : "../../abstractions/core",
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
				"name" : "oovu.dsp.8chlevels.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/dsp",
				"patcherrelativepath" : "../../abstractions/dsp",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.input~.voix.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.input~",
				"patcherrelativepath" : ".",
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
				"name" : "mxj oovu.Method.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/core",
				"patcherrelativepath" : "../../abstractions/core",
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
				"name" : "oovu.gui.meters.8x.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/abstractions/gui",
				"patcherrelativepath" : "../../abstractions/gui",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.input~.view.oscillator.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.input~",
				"patcherrelativepath" : ".",
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
				"patcherrelativepath" : ".",
				"type" : "JSON",
				"implicit" : 1
			}
, 			{
				"name" : "omod.input~.view.adc.maxpat",
				"bootpath" : "/Users/josiah/Documents/Freelance/barroso/oovu/modules/omod.input~",
				"patcherrelativepath" : ".",
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
 ]
	}

}

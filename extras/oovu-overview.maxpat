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
		"rect" : [ 653.0, 77.0, 585.0, 515.0 ],
		"bgcolor" : [ 0.0, 0.0, 0.0, 1.0 ],
		"bglocked" : 0,
		"openinpresentation" : 1,
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
					"id" : "obj-31",
					"maxclass" : "newobj",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 155.0, 635.0, 139.0, 20.0 ],
					"text" : "sprintf oovu-tutorials-%s"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-21",
					"maxclass" : "newobj",
					"numinlets" : 1,
					"numoutlets" : 5,
					"outlettype" : [ "", "", "", "", "" ],
					"patching_rect" : [ 155.0, 605.0, 137.0, 20.0 ],
					"text" : "regexp \\\\s @substitute -"
				}

			}
, 			{
				"box" : 				{
					"fontface" : 3,
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"frgb" : 0.0,
					"id" : "obj-10",
					"linecount" : 3,
					"maxclass" : "comment",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 12.0, 275.0, 140.0, 47.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 10.0, 375.0, 550.0, 20.0 ],
					"text" : "CORE CONCEPTS (documentation to be completed)",
					"textcolor" : [ 0.572549, 0.615686, 0.658824, 1.0 ],
					"textjustification" : 1
				}

			}
, 			{
				"box" : 				{
					"border" : 2.0,
					"id" : "obj-29",
					"justification" : 1,
					"linecolor" : [ 0.8, 0.8, 0.8, 1.0 ],
					"maxclass" : "live.line",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 425.0, 300.0, 10.0, 90.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 425.0, 405.0, 10.0, 90.0 ]
				}

			}
, 			{
				"box" : 				{
					"border" : 2.0,
					"id" : "obj-28",
					"justification" : 1,
					"linecolor" : [ 0.8, 0.8, 0.8, 1.0 ],
					"maxclass" : "live.line",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 146.0, 300.0, 10.0, 90.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 145.0, 405.0, 10.0, 90.0 ]
				}

			}
, 			{
				"box" : 				{
					"border" : 2.0,
					"id" : "obj-27",
					"justification" : 1,
					"linecolor" : [ 0.8, 0.8, 0.8, 1.0 ],
					"maxclass" : "live.line",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 283.0, 299.0, 10.0, 90.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 285.0, 405.0, 10.0, 90.0 ]
				}

			}
, 			{
				"box" : 				{
					"activesafe" : 0,
					"button" : 1,
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-26",
					"maxclass" : "tab",
					"numinlets" : 1,
					"numoutlets" : 3,
					"outlettype" : [ "int", "", "" ],
					"parameter_enable" : 0,
					"patching_rect" : [ 415.0, 405.0, 140.0, 160.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 430.0, 400.0, 140.0, 100.0 ],
					"spacing_x" : 16.0,
					"spacing_y" : 16.0,
					"tabs" : [ "Audio Routing", "The Mixer", "Event Scripting" ]
				}

			}
, 			{
				"box" : 				{
					"activesafe" : 0,
					"button" : 1,
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-25",
					"maxclass" : "tab",
					"numinlets" : 1,
					"numoutlets" : 3,
					"outlettype" : [ "int", "", "" ],
					"parameter_enable" : 0,
					"patching_rect" : [ 288.0, 406.0, 140.0, 160.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 290.0, 400.0, 140.0, 100.0 ],
					"spacing_x" : 16.0,
					"spacing_y" : 16.0,
					"tabs" : [ "Envelopes", "Bindings", "Patterns" ]
				}

			}
, 			{
				"box" : 				{
					"activesafe" : 0,
					"button" : 1,
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-24",
					"maxclass" : "tab",
					"numinlets" : 1,
					"numoutlets" : 3,
					"outlettype" : [ "int", "", "" ],
					"parameter_enable" : 0,
					"patching_rect" : [ 156.0, 406.0, 140.0, 160.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 150.0, 400.0, 140.0, 100.0 ],
					"spacing_x" : 16.0,
					"spacing_y" : 16.0,
					"tabs" : [ "Messages", "Proxies", "Datatypes" ]
				}

			}
, 			{
				"box" : 				{
					"activesafe" : 0,
					"button" : 1,
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-22",
					"maxclass" : "tab",
					"numinlets" : 1,
					"numoutlets" : 3,
					"outlettype" : [ "int", "", "" ],
					"parameter_enable" : 0,
					"patching_rect" : [ 16.0, 406.0, 140.0, 160.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 10.0, 400.0, 140.0, 100.0 ],
					"spacing_x" : 16.0,
					"spacing_y" : 16.0,
					"tabs" : [ "The Environment", "Module Structure", "OSC Addresses" ]
				}

			}
, 			{
				"box" : 				{
					"button" : 1,
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-18",
					"maxclass" : "tab",
					"numinlets" : 1,
					"numoutlets" : 3,
					"outlettype" : [ "int", "", "" ],
					"parameter_enable" : 0,
					"patching_rect" : [ 430.0, 95.0, 140.0, 160.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 430.0, 85.0, 140.0, 260.0 ],
					"spacing_x" : 16.0,
					"spacing_y" : 16.0,
					"tabs" : [ "oovu.Module", "oovu.Property", "oovu.Method", "oovu.Return", "oovu.Proxy", "oovu.DspSettings", "oovu.DspSend", "oovu.DspReceive" ]
				}

			}
, 			{
				"box" : 				{
					"border" : 2.0,
					"id" : "obj-17",
					"justification" : 1,
					"linecolor" : [ 0.8, 0.8, 0.8, 1.0 ],
					"maxclass" : "live.line",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 420.0, 95.0, 10.0, 160.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 425.0, 85.0, 10.0, 260.0 ]
				}

			}
, 			{
				"box" : 				{
					"fontface" : 3,
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"frgb" : 0.0,
					"id" : "obj-16",
					"maxclass" : "comment",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 430.0, 75.0, 140.0, 20.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 430.0, 65.0, 140.0, 20.0 ],
					"text" : "COMPONENTS",
					"textcolor" : [ 0.572549, 0.615686, 0.658824, 1.0 ],
					"textjustification" : 1
				}

			}
, 			{
				"box" : 				{
					"fontface" : 1,
					"fontname" : "Arial",
					"fontsize" : 42.0,
					"frgb" : 0.0,
					"id" : "obj-2",
					"maxclass" : "comment",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 20.0, 15.0, 135.0, 53.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 0.0, 0.0, 135.0, 53.0 ],
					"text" : "OOVU",
					"textcolor" : [ 0.8, 0.8, 0.8, 1.0 ]
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"frgb" : 0.0,
					"id" : "obj-4",
					"maxclass" : "comment",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 150.0, 40.0, 397.0, 20.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 130.0, 25.0, 247.0, 20.0 ],
					"text" : "a modular performance framework for Max 6"
				}

			}
, 			{
				"box" : 				{
					"fontface" : 1,
					"fontname" : "Arial",
					"fontsize" : 16.0,
					"frgb" : 0.0,
					"id" : "obj-11",
					"maxclass" : "comment",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 150.0, 20.0, 123.0, 24.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 130.0, 5.0, 123.0, 24.0 ],
					"text" : "oovu overview",
					"underline" : 1
				}

			}
, 			{
				"box" : 				{
					"border" : 2.0,
					"id" : "obj-14",
					"justification" : 1,
					"linecolor" : [ 0.8, 0.8, 0.8, 1.0 ],
					"maxclass" : "live.line",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 275.0, 95.0, 10.0, 90.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 285.0, 85.0, 10.0, 90.0 ]
				}

			}
, 			{
				"box" : 				{
					"border" : 2.0,
					"id" : "obj-13",
					"justification" : 1,
					"linecolor" : [ 0.8, 0.8, 0.8, 1.0 ],
					"maxclass" : "live.line",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 135.0, 95.0, 10.0, 120.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 145.0, 85.0, 10.0, 120.0 ]
				}

			}
, 			{
				"box" : 				{
					"fontface" : 3,
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"frgb" : 0.0,
					"id" : "obj-12",
					"maxclass" : "comment",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 280.0, 75.0, 140.0, 20.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 290.0, 65.0, 140.0, 20.0 ],
					"text" : "CONTROL",
					"textcolor" : [ 0.572549, 0.615686, 0.658824, 1.0 ],
					"textjustification" : 1
				}

			}
, 			{
				"box" : 				{
					"fontface" : 3,
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"frgb" : 0.0,
					"id" : "obj-7",
					"maxclass" : "comment",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 140.0, 75.0, 140.0, 20.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 150.0, 65.0, 140.0, 20.0 ],
					"text" : "TREATMENTS",
					"textcolor" : [ 0.572549, 0.615686, 0.658824, 1.0 ],
					"textjustification" : 1
				}

			}
, 			{
				"box" : 				{
					"fontface" : 3,
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"frgb" : 0.0,
					"id" : "obj-5",
					"maxclass" : "comment",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 0.0, 75.0, 140.0, 20.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 10.0, 65.0, 140.0, 20.0 ],
					"text" : "INPUTS & OUTPUTS",
					"textcolor" : [ 0.572549, 0.615686, 0.658824, 1.0 ],
					"textjustification" : 1
				}

			}
, 			{
				"box" : 				{
					"button" : 1,
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-3",
					"maxclass" : "tab",
					"numinlets" : 1,
					"numoutlets" : 3,
					"outlettype" : [ "int", "", "" ],
					"parameter_enable" : 0,
					"patching_rect" : [ 280.0, 95.0, 140.0, 30.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 290.0, 85.0, 140.0, 30.0 ],
					"spacing_x" : 16.0,
					"spacing_y" : 16.0,
					"tabs" : [ "omod.eventscript" ]
				}

			}
, 			{
				"box" : 				{
					"button" : 1,
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-1",
					"maxclass" : "tab",
					"numinlets" : 1,
					"numoutlets" : 3,
					"outlettype" : [ "int", "", "" ],
					"parameter_enable" : 0,
					"patching_rect" : [ 140.0, 95.0, 140.0, 90.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 150.0, 85.0, 140.0, 90.0 ],
					"spacing_x" : 16.0,
					"spacing_y" : 16.0,
					"tabs" : [ "omod.allpass~", "omod.panner~", "omod.reverb~" ]
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-9",
					"maxclass" : "newobj",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 5.0, 340.0, 100.0, 20.0 ],
					"text" : "pcontrol"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-8",
					"maxclass" : "newobj",
					"numinlets" : 1,
					"numoutlets" : 1,
					"outlettype" : [ "" ],
					"patching_rect" : [ 5.0, 315.0, 100.0, 20.0 ],
					"text" : "prepend help"
				}

			}
, 			{
				"box" : 				{
					"button" : 1,
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-6",
					"maxclass" : "tab",
					"numinlets" : 1,
					"numoutlets" : 3,
					"outlettype" : [ "int", "", "" ],
					"parameter_enable" : 0,
					"patching_rect" : [ 0.0, 95.0, 140.0, 120.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 10.0, 85.0, 140.0, 120.0 ],
					"spacing_x" : 16.0,
					"spacing_y" : 16.0,
					"tabs" : [ "omod.input~", "omod.sfplay~", "omod.bufferplay~", "omod.output~" ]
				}

			}
, 			{
				"box" : 				{
					"background" : 1,
					"bgcolor" : [ 1.0, 1.0, 1.0, 1.0 ],
					"bordercolor" : [ 0.094118, 0.113725, 0.137255, 1.0 ],
					"grad1" : [ 0.666667, 0.698039, 0.717647, 1.0 ],
					"grad2" : [ 0.094118, 0.113725, 0.137255, 1.0 ],
					"id" : "obj-20",
					"maxclass" : "panel",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 0.0, 250.0, 600.0, 50.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 0.0, 365.0, 585.0, 150.0 ],
					"prototypename" : "M4L.horizontal-black",
					"rounded" : 0
				}

			}
, 			{
				"box" : 				{
					"background" : 1,
					"bgcolor" : [ 1.0, 1.0, 1.0, 1.0 ],
					"bordercolor" : [ 0.094118, 0.113725, 0.137255, 1.0 ],
					"grad1" : [ 0.666667, 0.698039, 0.717647, 1.0 ],
					"grad2" : [ 0.094118, 0.113725, 0.137255, 1.0 ],
					"id" : "obj-15",
					"maxclass" : "panel",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 5.0, 15.0, 600.0, 50.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 0.0, 0.0, 585.0, 50.0 ],
					"prototypename" : "M4L.horizontal-black",
					"rounded" : 0
				}

			}
 ],
		"lines" : [ 			{
				"patchline" : 				{
					"destination" : [ "obj-8", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-1", 1 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-8", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-18", 1 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-31", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-21", 3 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-31", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-21", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-21", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-22", 1 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-21", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-24", 1 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-21", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-25", 1 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-21", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-26", 1 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-8", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-3", 1 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-8", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-31", 0 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-8", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-6", 1 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-9", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-8", 0 ]
				}

			}
 ],
		"dependency_cache" : [  ]
	}

}

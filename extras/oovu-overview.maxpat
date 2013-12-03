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
		"rect" : [ 572.0, 65.0, 438.0, 237.0 ],
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
					"frgb" : 0.0,
					"id" : "obj-16",
					"linecount" : 2,
					"maxclass" : "comment",
					"numinlets" : 1,
					"numoutlets" : 0,
					"patching_rect" : [ 40.0, 60.0, 150.0, 33.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 15.0, 45.0, 229.0, 20.0 ],
					"text" : "a modular performance system for Max 6"
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
					"patching_rect" : [ 275.0, 95.0, 10.0, 120.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 285.0, 100.0, 10.0, 120.0 ]
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
					"presentation_rect" : [ 145.0, 100.0, 10.0, 120.0 ]
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
					"presentation_rect" : [ 290.0, 80.0, 140.0, 20.0 ],
					"text" : "CONTROL",
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
					"presentation_rect" : [ 150.0, 80.0, 140.0, 20.0 ],
					"text" : "TREATMENTS",
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
					"presentation_rect" : [ 10.0, 80.0, 140.0, 20.0 ],
					"text" : "INPUTS & OUTPUTS",
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
					"presentation_rect" : [ 290.0, 100.0, 140.0, 30.0 ],
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
					"presentation_rect" : [ 150.0, 100.0, 140.0, 90.0 ],
					"spacing_x" : 16.0,
					"spacing_y" : 16.0,
					"tabs" : [ "omod.allpass~", "omod.panner~", "omod.reverb~" ]
				}

			}
, 			{
				"box" : 				{
					"align" : 0,
					"bgcolor" : [ 0.666667, 0.698039, 0.717647, 0.0 ],
					"bgoncolor" : [ 0.666667, 0.698039, 0.717647, 0.0 ],
					"bgovercolor" : [ 0.666667, 0.698039, 0.717647, 0.0 ],
					"bgoveroncolor" : [ 0.666667, 0.698039, 0.717647, 0.0 ],
					"border" : 1,
					"bordercolor" : [ 0.666667, 0.698039, 0.717647, 0.0 ],
					"borderoncolor" : [ 0.666667, 0.698039, 0.717647, 0.0 ],
					"fontface" : 3,
					"fontname" : "Arial Bold",
					"fontsize" : 24.0,
					"id" : "obj-10",
					"ignoreclick" : 1,
					"maxclass" : "textbutton",
					"mode" : 1,
					"numinlets" : 1,
					"numoutlets" : 3,
					"outlettype" : [ "", "", "int" ],
					"parameter_enable" : 0,
					"patching_rect" : [ 0.0, 0.0, 105.0, 35.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 15.0, 15.0, 80.0, 35.0 ],
					"prototypename" : "M4L.display",
					"rounded" : 0.0,
					"text" : "OOVU",
					"textcolor" : [ 0.301961, 0.337255, 0.403922, 1.0 ],
					"texton" : "Text",
					"textovercolor" : [ 1.0, 1.0, 1.0, 1.0 ],
					"textoveroncolor" : [ 1.0, 1.0, 1.0, 1.0 ]
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
					"presentation_rect" : [ 10.0, 100.0, 140.0, 120.0 ],
					"spacing_x" : 16.0,
					"spacing_y" : 16.0,
					"tabs" : [ "omod.input~", "omod.sfplay~", "omod.bufferplay~", "omod.output~" ]
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
					"source" : [ "obj-3", 1 ]
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

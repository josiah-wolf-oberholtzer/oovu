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
		"rect" : [ 560.0, 207.0, 640.0, 480.0 ],
		"bgcolor" : [ 1.0, 1.0, 1.0, 0.0 ],
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
					"id" : "obj-5",
					"maxclass" : "newobj",
					"numinlets" : 2,
					"numoutlets" : 2,
					"outlettype" : [ "", "" ],
					"patching_rect" : [ 60.0, 170.0, 118.0, 20.0 ],
					"text" : "substitute name text"
				}

			}
, 			{
				"box" : 				{
					"fontname" : "Arial",
					"fontsize" : 12.0,
					"id" : "obj-3",
					"maxclass" : "newobj",
					"numinlets" : 3,
					"numoutlets" : 3,
					"outlettype" : [ "", "", "" ],
					"patching_rect" : [ 60.0, 125.0, 116.0, 20.0 ],
					"text" : "mxj oovu.Proxy #1 ."
				}

			}
, 			{
				"box" : 				{
					"bgcolor" : [ 0.666667, 0.698039, 0.717647, 0.0 ],
					"bgoncolor" : [ 0.666667, 0.698039, 0.717647, 0.0 ],
					"bgovercolor" : [ 0.666667, 0.698039, 0.717647, 0.0 ],
					"bgoveroncolor" : [ 0.666667, 0.698039, 0.717647, 0.0 ],
					"border" : 1,
					"bordercolor" : [ 0.666667, 0.698039, 0.717647, 0.0 ],
					"borderoncolor" : [ 0.666667, 0.698039, 0.717647, 0.0 ],
					"fontname" : "Arial Bold",
					"fontsize" : 12.0,
					"id" : "obj-2",
					"ignoreclick" : 1,
					"maxclass" : "textbutton",
					"mode" : 1,
					"numinlets" : 1,
					"numoutlets" : 3,
					"outlettype" : [ "", "", "int" ],
					"parameter_enable" : 0,
					"patching_rect" : [ 60.0, 195.0, 95.0, 25.0 ],
					"presentation" : 1,
					"presentation_rect" : [ 0.0, 0.0, 195.0, 25.0 ],
					"prototypename" : "M4L.display",
					"rounded" : 0.0,
					"text" : "LOADING...",
					"textcolor" : [ 1.0, 1.0, 1.0, 1.0 ],
					"texton" : "Text",
					"textovercolor" : [ 1.0, 1.0, 1.0, 1.0 ],
					"textoveroncolor" : [ 1.0, 1.0, 1.0, 1.0 ]
				}

			}
 ],
		"lines" : [ 			{
				"patchline" : 				{
					"destination" : [ "obj-5", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-3", 2 ]
				}

			}
, 			{
				"patchline" : 				{
					"destination" : [ "obj-2", 0 ],
					"disabled" : 0,
					"hidden" : 0,
					"source" : [ "obj-5", 0 ]
				}

			}
 ]
	}

}

# Lam-Estimate
An android app to estimate lamination charges on the fly and make the work day more entertaining.
It was built to compensate for my lack of on-the-fly mathematical prowess with memes and a 
reasonable estimate.

This is the first android app I've made, so much of it is a learning process for me. 

![image](https://github.com/Adri6336/Lam-Estimate/blob/main/lamestimate.png)

# Getting an Estimate

The estimate is limited to orders that deal with multiple pages of the same dimensions. 
To get an estimate you need to fill in values for a few different required settings: paper side width, 
num per line, and page count.

## Required settings

**Paper Side Width**: this is the width of the sheet of paper as measured in inches. In the following image, the
side width is the highlighted portion of the paper.

![image](https://github.com/Adri6336/Lam-Estimate/blob/main/raw/sidewidth2.png)

**Num Per Line**: This represents the amount of sheets of paper that you can fit onto your 
feed table and laminate together.

**Page Count**: This represents the total amount of sheets of paper that you will need to laminate.

## Optional settings

These are the settings that have a predefined value. This value may be changed to make the estimation 
more accurate for your purposes.

**Space Between Pages**: This represents the average amount of space left between lines/rows of papers as measured in inches. 
The default value is based off of an average for me whenever I count four seconds down to two seconds between lines/rows of
lamination. This is probably the setting that will lead to the most inaccuracy; you should collect an average for yourself
to make the estimate better.

**Rate**: This is the amount of money per foot that will be charged for the lamination. Default setting
is 75 cents / ft.

**Min Charge**: This is the floor charge. If a lamination is super small, this is the lowest the charge is allowed
to be. Default setting is $1.25

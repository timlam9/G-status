package com.lamti.gstatus.ui.converters

/**
 * Calculates the indicator value using the following range converter formula:
 *
 * @param oldRange = (OldMax - OldMin)
 * @param newRange = (NewMax - NewMin)
 * @param newValue = (((OldValue - OldMin) * NewRange) / OldRange) + NewMin
 */
internal fun Float.toIndicatorValue(): Float {
    val oldRange = (1000 - 0)
    val newRange = (120 - (-120))

    return (((this - 0) * newRange) / oldRange) + (-120)
}

/**
 * Calculates the arc value using the following range converter formula:
 *
 * @param oldRange = (OldMax - OldMin)
 * @param newRange = (NewMax - NewMin)
 * @param newValue = (((OldValue - OldMin) * NewRange) / OldRange) + NewMin
 */
internal fun Float.toArcValue(): Float {
    val oldRange = (1000 - 0)
    val newRange = (238 - 0)

    return (((this - 0) * newRange) / oldRange) + (0)
}

/**
 * Calculates the Speedometer's value splitting the kbps range (0..1000) to 5 smaller ranges.
 *
 * The displaying UI units are: 0, 5, 10, 50, 100, 250, 500, 750, 1000.
 * The ranges are separated based on the equal steps of those numbers:
 *
 * a. 0..10, (step = 5)
 * b. 10..50, (step = 40)
 * c. 50..100, (step = 50)
 * d. 100..250, (step = 150)
 * e. 250..1000 (step = 250)
 *
 * In each range the kbps value is converted to the actual UI displaying unit. The Speedometer's (arc) range is 0..1000
 * and it is separated in 8 equal ranges. So each range has 125 points (125 * 8 = 1000).
 * So for the first range (a) the kbps value must be multiplied with 25 because 1 UI unit (displaying line) is equal to
 * 25 kbps points. We can come up with this conclusion if we take a look at the max value of the range which is 10.
 * So 10 * 25 = 250 which is equal with the first two ranges of the arc (125 * 2 = 250)
 * For the second range the kbps value must be multiplied with 3.125. If we follow the same logic we can see that
 * 3.125 * 40 is equal to 125. The max value of this range which is 50 is calculated by the first range's max, which is
 * 10, adding the remaining second range's max which is 40 (50 - 10 = 40). So in order to calculate the Speedometer's
 * value we need to get the first range's max value (25f * 10) and add the calculated value of the second range
 * (3.125f * kbps value - 10). The same logic applies to the next ranges.
 */
internal fun Float.toSpeedometerValue(): Float = when (this) {
    in 0f..10f -> 25f * this
    in 10f..50f -> (3.125f * (this - 10f)) + 25f * 10f
    in 50f..100f -> (2.5f * (this - 50f)) + 25f * 10f + 3.125f * 40f
    in 100f..250f -> ((5f / 6f) * (this - 100f)) + 25f * 10f + 3.125f * 40f + 2.5f * 50f
    in 250f..1000f -> (0.5f * (this - 250f)) + 25f * 10f + 3.125f * 40f + 2.5f * 50f + (5f / 6f) * 150f
    else -> this
}

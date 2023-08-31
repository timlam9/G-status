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

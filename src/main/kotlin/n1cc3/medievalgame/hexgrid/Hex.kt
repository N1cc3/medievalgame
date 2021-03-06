package n1cc3.medievalgame.hexgrid

/**
 * A single cell for a hexagonal grid system.
 */
data class Hex(val x: Int, val y: Int) {

	private val parity: Int = y % 2

	/**
	 * Returns the immediate neighbor [Hex]es.
	 */
	fun neighbors(): List<Hex> = directions[parity].map { this + it }

	/**
	 * Calculates the manhattan distance between this and given [Hex].
	 */
	fun distanceTo(other: Hex): Int = this.toCube().distanceTo(other.toCube())

	/**
	 * Returns a list of [Hex]es which are within given range.
	 */
	fun neighborsWithin(range: Int): List<Hex> = this.toCube().neighborsWithin(range).map { it.toHex() }

	/**
	 * For checking if hex is inside terrain. From 0 to specified max values.
	 * @param maxX Map width.
	 * @param maxY Map height.
	 * @return true if inside
	 */
	fun isInside(maxX: Int, maxY: Int): Boolean = this.x in 0..(maxX - 1) && this.y in 0..(maxY - 1)

	private fun toCube(): Cube {
		val cx = x - (y - parity) / 2
		val cz = y
		val cy = -x - cz
		return Cube(cx, cy, cz)
	}

	operator fun plus(that: Hex): Hex = Hex(this.x + that.x, this.y + that.y)

	operator fun minus(that: Hex): Hex = Hex(this.x - that.x, this.y - that.y)

	companion object {
		private val evenDirections = arrayOf(
			Hex(1, 0),
			Hex(0, -1),
			Hex(-1, -1),
			Hex(-1, 0),
			Hex(-1, 1),
			Hex(0, 1)
		)
		private val oddDirections = arrayOf(
			Hex(1, 0),
			Hex(1, -1),
			Hex(0, -1),
			Hex(-1, 0),
			Hex(0, 1),
			Hex(1, 1)
		)
		private val directions = arrayOf(evenDirections, oddDirections)
	}

}

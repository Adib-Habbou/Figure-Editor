/**
 *
 */
package figures;

import java.util.logging.Logger;

import figures.enums.LineType;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import utils.ColorFactory;

/**
 * Polygon Figure containing a {@link javafx.scene.shape.Polygon} as its
 * {@link Figure#shape}
 * @warning Since This class is also named "Polygon", you'll need to use
 * (javafx.scene.shape.Polygon) each time you need to acces to internal
 * {@link Figure#shape} casted as a {@link javafx.scene.shape.Polygon}
 * @implSpec It is assumed that {@link Figure#shape} will always be non null
 * during the life cycle of a Polygon.
 * @author davidroussel
 */
public class Polygon extends Figure
{
	/**
	 * Instances counter (to be used in {@link Figure#instanceNumber}) of each
	 * Polygon.
	 * @implNote No need to decrease {@link Figure#instanceNumber} in
	 * {@link #finalize()}
	 */
	private static int counter = 0;

	/**
	 * Valued constructor to build a zero size Polygon at point (x, y).
	 * Used during Cicle construction with {@link MouseEvent}s
	 * Calls super-constructor, sets {@link Figure#instanceNumber} then
	 * {@link #createShape(double, double)} and attach {@link Figure#shape} to
	 * {@link Figure#root}.
	 * @param fillColor the fill color (or null if there is no fill color).
	 * The fill color set in this Polygon shall be set from {@link ColorFactory}.
	 * @param edgeColor the edge color (or null if there is no edge color)
	 * The edge color set in this Polygon shall be set from {@link ColorFactory}.
	 * @param lineType line type (Either {@link LineType#SOLID},
	 * {@link LineType#DASHED} or {@link LineType#NONE}). If there is no edge
	 * color provided the internal {@link #lineType} shall be set to
	 * {@link LineType#NONE}
	 * @param lineWidth line width of this Polygon. If there is no edge
	 * color provided the internal {@link #lineType} shall be set to 0
	 * @param parentLogger a parent logger used to initialize the current logger
	 * @param x the initial x coordinate in the drawing panel where to create this Polygon
	 * @param y the initial y coordinate in the drawing panel where to create this Polygon
	 * @throws IllegalStateException if we try to set both fillColor and
	 * edgecolor as nulls
	 */
	public Polygon(Color fillColor,
	              Color edgeColor,
	              LineType lineType,
	              double lineWidth,
	              Logger parentLogger,
	              double x,
	              double y)
	    throws IllegalStateException
	{
		super(fillColor, edgeColor, lineType, lineWidth, parentLogger);
		instanceNumber = counter++;
		createShape(x, y);
		root.getChildren().add(shape);
	}

	/**
	 * Valued constructor to build a Polygon at point (x, y) with specified radius
	 * Calls super-constructor, sets {@link Figure#instanceNumber} then
	 * {@link #createShape(double, double)} and attach {@link Figure#shape} to
	 * {@link Figure#root}.
	 * @param fillColor the fill color (or null if there is no fill color).
	 * The fill color set in this Polygon shall be set from {@link ColorFactory}.
	 * @param edgeColor the edge color (or null if there is no edge color)
	 * The edge color set in this Polygon shall be set from {@link ColorFactory}.
	 * @param lineType line type (Either {@link LineType#SOLID},
	 * {@link LineType#DASHED} or {@link LineType#NONE}). If there is no edge
	 * color provided the internal {@link #lineType} shall be set to
	 * {@link LineType#NONE}
	 * @param lineWidth line width of this Polygon. If there is no edge
	 * color provided the internal {@link #lineType} shall be set to 0
	 * @param parentLogger a parent logger used to initialize the current logger
	 * @param x the initial x coordinate in the drawing panel where to create this Polygon
	 * @param y the initial y coordinate in the drawing panel where to create this Polygon
	 * @param radius the initial radius of the Polygon
	 * @throws IllegalStateException if we try to set both fillColor and
	 * edgecolor as nulls
	 */
	public Polygon(Color fillColor,
	              Color edgeColor,
	              LineType lineType,
	              double lineWidth,
	              Logger parentLogger,
	              double x,
	              double y,
	              double radius)
	    throws IllegalStateException
	{
		this(fillColor, edgeColor, lineType, lineWidth, parentLogger, x, y);
		javafx.scene.shape.Polygon Polygon = (javafx.scene.shape.Polygon) shape;
	}

	/**
	 * Copy constructor
	 * @param figure the figure to be copied
	 * @throws IllegalArgumentException if the provided figure is not a Polygon
	 */
	public Polygon(Figure figure) throws IllegalArgumentException
	{
		super(figure);
		if (!(figure instanceof Polygon))
		{
			String message = "provided figure is not a Polygon: "
				+ figure.getClass().getSimpleName();
			logger.severe(message);
			throw new IllegalArgumentException(message);
		}
		javafx.scene.shape.Polygon figurePolygon = (javafx.scene.shape.Polygon) figure.shape;
		//shape = new javafx.scene.shape.Polygon(figurePolygon.getCenterX(), figurePolygon.getCenterY(), figurePolygon.getRadius());
		root.getChildren().add(shape);
		applyParameters(shape);
		setSelected(figure.selected);
	}


	/**
	 * Convenience method to get internal {@link Figure#shape} casted as a
	 * {@link javafx.scene.shape.Polygon}
	 * @return the internal {@link Figure#shape} casted as a
	 * {@link javafx.scene.shape.Polygon}
	 */
	private javafx.scene.shape.Polygon getPolygonShape()
	{
		return (javafx.scene.shape.Polygon)shape;
	}

	/**
	 * Creates actual {@link #shape} at specified position and apply
	 * parameters
	 * @param x the x coordinate of the initial point where to create the new shape
	 * @param y the y coordinate of the initial point where to create the new shape
	 * @post a new {@link #shape} has been created with a new
	 * {@link #instanceNumber} with {@link #fillColor}, {@link #edgeColor},
	 * {@link #lineType} & {@link #lineWidth} applied with
	 * {@link #applyParameters(Shape)}
	 */
	@Override
	public void createShape(double x, double y)
	{
		/*
		 * Note: since This class is also named Polygon we need to explicitely
		 * use "new javafx.scene.shape.Polygon(...)" here
		 */
		shape = new javafx.scene.shape.Polygon(x, y, 0.0);
		applyParameters(shape);
	}
	
	/**
	 * Creates a copy of this Polygon (with the same name and instance number)
	 * @return A distinct copy of this Polygon
	 */
	@Override
	public Figure clone()
	{
		return new Polygon(this);
	}

	/**
	 * Compare this Polygon to another figure
	 * @return true if the other figure is also a Polygon with the same
	 * position and size (with 1e-6 threhold), false otherwise.
	 * Other parameters, such as {@link Figure#fillColor},
	 * {@link Figure#edgeColor}, {@link Figure#lineType} and
	 * {@link Figure#lineWidth} are checked in {@link Figure#equals(Object)}
	 * @see Figure#equals(Object)
	 */
	@Override
	protected boolean equals(Figure figure)
	{
		if (!(figure instanceof Polygon))
		{
			return false;
		}

		Polygon Polygon = (Polygon) figure;

		if (Math.abs(getCenter().distance(Polygon.getCenter())) > Figure.threshold)
		{
			return false;
		}

		if (Math.abs(getRadius() - Polygon.getRadius()) > Figure.threshold)
		{
			return false;
		}

		return true;
	}

	private int getRadius() {
		return 0;
	}

	@Override
	public Point2D getCenter() {
		return null;
	}

	@Override
	public double width() {
		return 0;
	}

	@Override
	public double height() {
		return 0;
	}

	@Override
	public Point2D topLeft() {
		return null;
	}

	@Override
	public Point2D bottomRight() {
		return null;
	}

	@Override
	public void setLastPoint(Point2D lastPoint) {
	}
}

/**
 * FastTilePlot - Callback for FastTilePlot button.
 * @author plessel.todd@epa.gov 2008-09-16
 * @version $Revision$ $Date$
 **/

package anl.verdi.plot.gui.action;

import java.awt.event.ActionEvent;
import java.util.List;

import org.apache.logging.log4j.LogManager;		// 2014
import org.apache.logging.log4j.Logger;			// 2014 replacing System.out.println with logger messages

import saf.core.ui.actions.AbstractSAFAction;
import anl.verdi.core.VerdiApplication;
import anl.verdi.data.DataFrame;
import anl.verdi.data.Dataset;
import anl.verdi.formula.Formula;
import anl.verdi.plot.gui.Plot;
import anl.verdi.plot.gui.PlotPanel;

public class FastTilePlot extends AbstractSAFAction<VerdiApplication> {

	// FastTilePlot button callback:

	/**
	 * 
	 */
	private static final long serialVersionUID = 7433688932017847111L;
	static final Logger Logger = LogManager.getLogger(anl.verdi.plot.gui.action.FastTilePlot.class.getName());
	
	public void actionPerformed( ActionEvent unused ) {
		Logger.debug("in action.FastTilePlot.actionPerformed");
	    final VerdiApplication application = workspace.getApplicationMediator();
	    application.getGui().busyCursor();

	    try {
	    if ( application.getProject().getSelectedFormula() != null ) {
		  Logger.debug("getSelectedFormula() != null; ready to calculate dataFrame");
	      final DataFrame dataFrame =
	        application.evaluateFormula( Formula.Type.TILE );
	      Logger.debug("look at dataFrame, check if null");

	      if ( dataFrame != null ) {
	    	  Logger.debug("dataFrame is not null; ready to generate Plot for FastTilePlot");
	    	  List<Dataset> datasets = dataFrame.getDataset();
	    	  
	    	  if (datasets != null && datasets.size() > 0 && datasets.get(0).getClass().getName().toLowerCase().indexOf("mpas") != -1) {
	    		  MeshPlot.performAction(application, dataFrame);
	    		  application.getGui().defaultCursor();
	    		  return;
	    	  }
		        final Plot plot = new anl.verdi.plot.gui.FastTilePlot(application, dataFrame );
				final String variableName = dataFrame.getVariable().getName();
				Logger.debug("have variableName = " + variableName);	// O3[1]
				Logger.debug("ready to generate PlotPanel for a plot, variableName, and additional values");
		        final PlotPanel panel = new PlotPanel( plot, "Tile " + variableName);
		        Logger.debug("ready to call addPlot to add the new panel to application GUI");
		        application.getGui().addPlot( panel );
		        Logger.debug("ready to add plot listener");
		        panel.addPlotListener( application );
		        Logger.debug("all done with actionPerformed");
	      } 
	    }
	    } catch (Throwable t) {
    	    Logger.error("An error occurred while rendering the plot", t);
    	    application.getGui().showError("Error", "An error occurred while rendering the plot");
	    }
	    application.getGui().defaultCursor();
	  }

}
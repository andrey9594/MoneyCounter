package moneycounter.views;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

import moneycounter.model.OperationData;

/**
 * <p>
 * Comparator for sorting table with OperationData data
 */
public class MyViewerComparator extends ViewerComparator
{
    private int propertyIndex;

    private static final int DESCENDING = 1;

    private int direction = DESCENDING;


    public MyViewerComparator()
    {
        this.propertyIndex = 0;
        this.direction = DESCENDING;
    }


    public int getDirection()
    {
        return direction == 1 ? SWT.DOWN : SWT.UP;
    }


    public void setColumn(int column)
    {
        if (column == this.propertyIndex)
        {
            // Same column as last sort; toggle the direction
            direction = 1 - direction;
        }
        else
        {
            // New column; do an ascending sort
            this.propertyIndex = column;
            direction = DESCENDING;
        }
    }


    @Override
    public int compare(Viewer viewer, Object e1, Object e2)
    {
        OperationData p1 = (OperationData)e1;
        OperationData p2 = (OperationData)e2;
        int rc = 0;
        switch (propertyIndex)
        {
            case 0: // ID
                rc = new Integer(p1.getId()).compareTo(p2.getId());
                break;
            case 1: // Дата и время добавления
                rc = p1.getTimestamp().compareTo(p2.getTimestamp());
                break;
            case 2: // Тип операции
                rc = p1.getOperationType().name().compareTo(p2.getOperationType().name());
                break;
            case 3: // Дата операции
                rc = p1.getDate().compareTo(p2.getDate());
                break;
            case 4: // Категория
                rc = p1.getCategory().name().compareTo(p2.getCategory().name());
                break;
            case 5: // Сумма
            	rc = new Double(p1.getSum()).compareTo(p2.getSum());
                break;
            case 6: // Комментарий
                rc = p1.getComment().compareTo(p2.getComment());
                break;
            default:
                rc = 0;
        }
        // If descending order, flip the direction
        if (direction == DESCENDING)
        {
            rc = -rc;
        }
        return rc;
    }
}
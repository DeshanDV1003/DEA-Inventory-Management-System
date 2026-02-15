package inventorymanagement.stock_service.service;

import inventorymanagement.stock_service.model.Stock;
import inventorymanagement.stock_service.repository.StockDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    @Autowired
    StockDAO stockDAO;

    public ResponseEntity<String> insertStock(Stock stock) {
        try{
            stockDAO.save(stock);
            return new ResponseEntity<>("Succeed", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Failed", HttpStatus.NOT_IMPLEMENTED);

        //ToDo
        //need to call asset service to confirm that the asset is added already
        //if not the asset should be added at first ---> not a task
        //check whether warehouse exists and have enough space
        //therefor I have to check stock quantity and available space.
        //if space is enough to stock the record should be saved.
    }

    public ResponseEntity<List<Stock>> viewStocks() {
        try {
            return new ResponseEntity<>(stockDAO.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(stockDAO.findAll(), HttpStatus.NOT_FOUND);

    }


    public ResponseEntity<String> deleteStockById(Long id) {
        try{
            stockDAO.deleteById(id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Deleted",HttpStatus.NOT_FOUND);
    }
}

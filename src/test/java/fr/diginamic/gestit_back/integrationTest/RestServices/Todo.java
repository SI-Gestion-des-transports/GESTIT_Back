package fr.diginamic.gestit_back.integrationTest.RestServices;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Todo {
    private Long id;
    private String title;
    private Boolean completed;
    private Long userId;

    public Todo(){
        
    }

    
    
}

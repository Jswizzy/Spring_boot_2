// tag::head[]
package tacos;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient.Type;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {

//end::head[]

    //tag::showDesignForm[]
    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = List.of(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );

        Arrays.stream(Type.values()).forEach(type ->
                model.addAttribute(
                        type.toString()
                                .toLowerCase(),
                        filterByType(ingredients, type)));

        model.addAttribute("design", new Design());

        return "design";
    }

//end::showDesignForm[]

/*
//tag::processDesign[]
  @PostMapping
  public String processDesign(Design design) {
    // Save the taco design...
    // We'll do this in chapter 3
    log.info("Processing design: " + design);
    
    return "redirect:/orders/current";
  }
  
//end::processDesign[]
 */

    //tag::processDesignValidated[]
    @PostMapping
    public String processDesign(@Valid Design design, Errors errors) {
        if (errors.hasErrors()) {

            List<FieldError> fieldErrors = errors.getFieldErrors();
            for (FieldError e : fieldErrors) {
                System.out.println(" -- " + e.getField());
            }

            return "design";
        }

        // Save the taco design...
        // We'll do this in chapter 3
        log.info("Processing design: " + design);

        return "redirect:/orders/current";
    }

//end::processDesignValidated[]

    //tag::filterByType[]
    private List<Ingredient> filterByType(
            List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

//end::filterByType[]
//tag::foot[]	
}
//end::foot[]
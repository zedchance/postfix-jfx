# postfix-jfx

**PURPOSE OF PROJECT**: Postfix evaluation  
**DATE**: 20190403  
**AUTHORS**: Zed Chance  

![demo](src/images/demo.png)

*PostfixGUI.java* is the postfix expression evaluator  
*GUI.java* is the postfix calculator  

To run in IntelliJ:  
Edit run configuration's VM options to include replacing `/path/to` with proper path
```
--module-path /path/to/javafx-sdk-11.0.2/lib --add-modules javafx.fxml,javafx.controls,javafx.media
```


## Postfix expressions
| Operator | Description                                |
| -------- | ------------------------------------------ |
| `+`      | add                                        |
| `-`      | subtract                                   |
| `*`      | multiply                                   |
| `/`      | divide                                     |
| `^`      | exponent                                   |
| `!`      | factorial                                  |
| `sqrt`   | square root                                |
| `pi`     | push pi onto stack                         |
| `sin`    | sine                                       |
| `cos`    | cosine                                     |
| `tan`    | tangent                                    |
| `ln`     | natural log                                |
| `mod`    | modulo (can also use `%`)                  |
| `dup`    | duplicate                                  |
| `swap`   | swap top 2 items                           |
| `rot`    | rotate items                               |
| `max`    | leaves the higher of 2 values on the stack |
| `min`    | leaves the lower of 2 values on the stack  |
| `dist`   | distance formula (distance to origin)      |

## Extra features implemented
- Calculator buttons
- Stack visualization
- Factorial (!)
- Pi
- Trigonometric functions
- Natural log
- Modulo
- Degrees to radians
- Radians to degrees
- Feet to smoots
- Error handling
- ANS Button (on PostfixGUI.java)
- Background image
- Sound effects
- Memory
- Keyboard bindings

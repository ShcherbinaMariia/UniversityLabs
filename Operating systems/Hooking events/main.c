#include <ApplicationServices/ApplicationServices.h>

// to compile use gcc -o main main.c -framework ApplicationServices

CGEventRef customCallback(CGEventTapProxy proxy, CGEventType eventType, CGEventRef event, void* p){
    CGKeyCode keycode = (CGKeyCode)CGEventGetIntegerValueField(event, kCGKeyboardEventKeycode);
    
    fprintf(stdout, "%d\n", keycode);

    // replace letter 'a' with letter 'm'
    if (keycode == (CGKeyCode)0)
        CGEventSetIntegerValueField(event, kCGKeyboardEventKeycode, (CGKeyCode)46);

    return event;
}

int main(void) {
    CGEventMask mask = CGEventMaskBit(kCGEventKeyDown) | CGEventMaskBit(kCGEventKeyUp);
    CFMachPortRef eventTap = CGEventTapCreate(kCGSessionEventTap,
                 kCGTailAppendEventTap, kCGEventTapOptionDefault, 
                 mask, customCallback, NULL);

    CFRunLoopSourceRef runLoopSource = CFMachPortCreateRunLoopSource(
        kCFAllocatorDefault, eventTap, 0);

    CFRunLoopAddSource(CFRunLoopGetCurrent(), runLoopSource,
                       kCFRunLoopCommonModes);
    CGEventTapEnable(eventTap, true);
    CFRunLoopRun();
    
    exit(0);
}
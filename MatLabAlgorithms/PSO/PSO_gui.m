function varargout = PSO_gui(varargin)
% PSO_gui MATLAB code for PSO_gui.fig
%      PSO_gui, by itself, creates a new PSO_gui or raises the existing
%      singleton*.
%
%      H = PSO_gui returns the handle to a new PSO_gui or the handle to
%      the existing singleton*.
%
%      PSO_gui('CALLBACK',hObject,eventData,handles,...) calls the local
%      function named CALLBACK in PSO_gui.m with the given input arguments.
%
%      PSO_gui('Property','Value',...) creates a new PSO_gui or raises the
%      existing singleton*.  Starting from the left, property value pairs are
%      applied to the GUI before PSO_gui_OpeningFcn gets called.  An
%      unrecognized property name or invalid value makes property application
%      stop.  All inputs are passed to PSO_gui_OpeningFcn via varargin.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Edit the above text to modify the response to help PSO_gui

% Last Modified by GUIDE v2.5 02-Aug-2015 21:25:03

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @PSO_gui_OpeningFcn, ...
                   'gui_OutputFcn',  @PSO_gui_OutputFcn, ...
                   'gui_LayoutFcn',  [] , ...
                   'gui_Callback',   []);
if nargin && ischar(varargin{1})
    gui_State.gui_Callback = str2func(varargin{1});
end

if nargout
    [varargout{1:nargout}] = gui_mainfcn(gui_State, varargin{:});
else
    gui_mainfcn(gui_State, varargin{:});
end
% End initialization code - DO NOT EDIT


% --- Executes just before PSO_gui is made visible.
function PSO_gui_OpeningFcn(hObject, eventdata, handles, varargin)
% This function has no output args, see OutputFcn.
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
% varargin   command line arguments to PSO_gui (see VARARGIN)

% Choose default command line output for PSO_gui
handles.output = hObject;

% Update handles structure
guidata(hObject, handles);

% UIWAIT makes PSO_gui wait for user response (see UIRESUME)
% uiwait(handles.figure1);


% --- Outputs from this function are returned to the command line.
function varargout = PSO_gui_OutputFcn(hObject, eventdata, handles) 
% varargout  cell array for returning output args (see VARARGOUT);
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Get default command line output from handles structure
varargout{1} = handles.output;


% --- Executes when figure1 is resized.
function figure1_SizeChangedFcn(hObject, eventdata, handles)
% hObject    handle to figure1 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)


% --- Executes on button press in PSO_button.
function PSO_button_Callback(hObject, eventdata, handles)
% hObject    handle to PSO_button (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
	h = findobj('Tag','inertia_weight_slider');
	weight = h.UserData;
    h = findobj('Tag','cognitive_slider');
    accCoeff = h.UserData;
    h = findobj('Tag','max_iterations_slider');
	max_num_iter = h.UserData;
	h = findobj('Tag','pop_size_slider');
	pop_size = h.UserData;
    set(handles.plotdescription,'String','The best cost solution is shown in blue');
    set(handles.soln, 'String', ' ');
    set(handles.solnitems, 'String', ' ');
    set(handles.bestsoln,'String',' ');
    set(handles.numit,'String',' ');
    set(handles.timePerIt,'String',' ');
    [results, solutionStore, solutionItems] = PSO(weight, accCoeff, max_num_iter, pop_size);
    
    set(handles.bestsoln,'String',num2str(results(1)));
    set(handles.numit,'String',num2str(results(2)));
    set(handles.timePerIt, 'String', strcat(num2str(results(3)), 's'));
    set(handles.plotdescription,'String','The best cost solution is shown in blue while the minimum solution for each iteration is shown in red');
    set(handles.errormessage,'String',' ');
    
    route = solutionStore;
    
    purchaseitems = solutionItems;
    set(handles.soln, 'String', route);
    set(handles.solnitems, 'String', purchaseitems);
    set(handles.errormessage, 'String', 'Finished Run');


% --- Executes on slider movement.
function pop_size_slider_Callback(hObject, eventdata, handles)
% hObject    handle to pop_size_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
val = floor(get(hObject,'Value'));   
set(hObject,'UserData',val); 
set(handles.popval,'String',num2str(val));
% Hints: get(hObject,'Value') returns position of slider
%        get(hObject,'Min') and get(hObject,'Max') to determine range of slider


% --- Executes during object creation, after setting all properties.
function pop_size_slider_CreateFcn(hObject, eventdata, handles)
% hObject    handle to pop_size_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: slider controls usually have a light gray background.
if isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor',[.9 .9 .9]);
end
set(hObject,'Max',50,'Min',5,'Value',10, 'SliderStep', [1/45 , 1/45]);


 val = get(hObject,'Value');   
set(hObject,'UserData',val);  







% --- Executes on slider movement.
function max_iterations_slider_Callback(hObject, eventdata, handles)
% hObject    handle to max_iterations_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
val = floor(get(hObject,'Value'));   
set(hObject,'UserData',val); 
set(handles.genval,'String',num2str(val));
% Hints: get(hObject,'Value') returns position of slider
%        get(hObject,'Min') and get(hObject,'Max') to determine range of slider


% --- Executes during object creation, after setting all properties.
function max_iterations_slider_CreateFcn(hObject, eventdata, handles)
% hObject    handle to max_iterations_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: slider controls usually have a light gray background.
if isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor',[.9 .9 .9]);
end
set(hObject,'Max',10000,'Min',1000,'Value',5000, 'SliderStep',[1/9000 , 1/9000 ]);
val = get(hObject,'Value');   
set(hObject,'UserData',val); 


% --- Executes on slider movement.
function inertia_weight_slider_Callback(hObject, eventdata, handles)
% hObject    handle to inertia_weight_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

val = get(hObject,'Value');   
set(hObject,'UserData',val);   
set(handles.inertiaval,'String',num2str(val));



% Hints: get(hObject,'Value') returns position of slider
%        get(hObject,'Min') and get(hObject,'Max') to determine range of slider


% --- Executes during object creation, after setting all properties.
function inertia_weight_slider_CreateFcn(hObject, eventdata, handles)
% hObject    handle to inertia_weight_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: slider controls usually have a light gray background.
if isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor',[.9 .9 .9]);
end
set(hObject,'Max',0.9,'Min',0.5,'Value',0.6);
val = get(hObject,'Value');   
set(hObject,'UserData',val); 
%set(hObject,'SliderStep',[ 0.1])



function popval_Callback(hObject, eventdata, handles)
% hObject    handle to popval (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
valstr = get(hObject,'String');  
if all(ismember(valstr, '0123456789+-.eEdD'))
    val = str2num(valstr);
    set(hObject,'UserData',floor(val));
    if val <= 5
        set(handles.pop_size_slider,'Value',5, 'UserData', 5);
        set(hObject,'String', num2str(5) );
    elseif val >= 50
        set(handles.pop_size_slider,'Value',50, 'UserData', 50);
        set(hObject,'String', num2str(50) );
    else 
        set(handles.pop_size_slider,'Value',floor(val), 'UserData', floor(val));
        set(hObject,'String', num2str(floor(val)));
    end
else 
    set(handles.errormessage,'String',strcat( valstr, ' is not a valid population number' ));
    h = findobj('Tag','pop_size_slider');
    val = h.UserData;
	set(hObject,'String', num2str(val));
end 
% Hints: get(hObject,'String') returns contents of popval as text
%        str2double(get(hObject,'String')) returns contents of popval as a double


% --- Executes during object creation, after setting all properties.
function popval_CreateFcn(hObject, eventdata, handles)
% hObject    handle to popval (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end

function genval_Callback(hObject, eventdata, handles)
% hObject    handle to genval (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
valstr = get(hObject,'String');  
if all(ismember(valstr, '0123456789+-.eEdD'))
    val = str2num(valstr);
    set(hObject,'UserData',floor(val));
    if val <= 1000
        set(handles.max_iterations_slider,'Value',1000, 'UserData', 1000);
        set(hObject,'String', num2str(1000) );
    elseif val >= 10000
        set(handles.max_iterations_slider,'Value',10000, 'UserData', 10000);
        set(hObject,'String', num2str(10000) );
    else 
        set(handles.max_iterations_slider,'Value',floor(val), 'UserData', floor(val));
        set(hObject,'String', num2str(floor(val)));
    end
else 
    set(handles.errormessage,'String',strcat( valstr, ' is not a valid population number' ));
    h = findobj('Tag','max_iterations_slider');
    val = h.UserData;
	set(hObject,'String', num2str(val));
end 

% Hints: get(hObject,'String') returns contents of genval as text
%        str2double(get(hObject,'String')) returns contents of genval as a double


% --- Executes during object creation, after setting all properties.
function genval_CreateFcn(hObject, eventdata, handles)
% hObject    handle to genval (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end



function inertiaval_Callback(hObject, eventdata, handles)
% hObject    handle to inertiaval (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of inertiaval as text
%        str2double(get(hObject,'String')) returns contents of inertiaval as a double

valstr = get(hObject,'String');  
if all(ismember(valstr, '0123456789+-.eEdD'))
    val = str2num(valstr);
    set(hObject,'UserData',floor(val));
    if val <= 0.5
        set(handles.inertia_weight_slider,'Value',0.5, 'UserData', 0.5);
        set(hObject,'String', num2str(0.5) );
    elseif val >= 0.9
        set(handles.inertia_weight_slider,'Value',0.9, 'UserData', 0.9);
        set(hObject,'String', num2str(0.9) );
    else 
        set(handles.inertia_weight_slider,'Value',val, 'UserData', val);
        set(hObject,'String', num2str(val));
    end
else 
    set(handles.errormessage,'String',strcat( valstr, ' is not a valid inertia value' ));
    h = findobj('Tag','inertia_weight_slider');
    val = h.UserData;
	set(hObject,'String', num2str(val));
end 

% --- Executes during object creation, after setting all properties.
function inertiaval_CreateFcn(hObject, eventdata, handles)
% hObject    handle to inertiaval (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on slider movement.
function cognitive_slider_Callback(hObject, eventdata, handles)
% hObject    handle to cognitive_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
val = get(hObject,'Value');   
set(hObject,'UserData',val); 
set(handles.cogval,'String',num2str(val));
% Hints: get(hObject,'Value') returns position of slider
%        get(hObject,'Min') and get(hObject,'Max') to determine range of slider


% --- Executes during object creation, after setting all properties.
function cognitive_slider_CreateFcn(hObject, eventdata, handles)
% hObject    handle to cognitive_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: slider controls usually have a light gray background.
if isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor',[.9 .9 .9]);
end
set(hObject,'Max',3,'Min',0.5,'Value',1.1, 'SliderStep',[1/25 , 1/25 ]);

 val = get(hObject,'Value');   
set(hObject,'UserData',val);  


function cogval_Callback(hObject, eventdata, handles)
% hObject    handle to cogval (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of cogval as text
%        str2double(get(hObject,'String')) returns contents of cogval as a double
valstr = get(hObject,'String');  
if all(ismember(valstr, '0123456789+-.eEdD'))
    val = str2num(valstr);
    set(hObject,'UserData',floor(val));
    if val <= 0.5
        set(handles.cognitive_slider,'Value',0.5, 'UserData', 0.5);
        set(hObject,'String', num2str(0.5) );
    elseif val >= 3
        set(handles.cognitive_slider,'Value',3.0, 'UserData', 3.0);
        set(hObject,'String', num2str(3.0) );
    else 
        set(handles.cognitive_slider,'Value',val, 'UserData', val);
        set(hObject,'String', num2str(val));
    end
else 
    set(handles.errormessage,'String',strcat( valstr, ' is not a valid cognitive coefficient value' ));
    h = findobj('Tag','cognitive_slider');
    val = h.UserData;
	set(hObject,'String', num2str(val));
end

% --- Executes during object creation, after setting all properties.
function cogval_CreateFcn(hObject, eventdata, handles)
% hObject    handle to cogval (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end

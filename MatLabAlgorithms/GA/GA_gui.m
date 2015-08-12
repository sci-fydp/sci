function varargout = GA_gui(varargin)
% GA_GUI MATLAB code for GA_gui.fig
%      GA_GUI, by itself, creates a new GA_GUI or raises the existing
%      singleton*.
%
%      H = GA_GUI returns the handle to a new GA_GUI or the handle to
%      the existing singleton*.
%
%      GA_GUI('CALLBACK',hObject,eventData,handles,...) calls the local
%      function named CALLBACK in GA_GUI.M with the given input arguments.
%
%      GA_GUI('Property','Value',...) creates a new GA_GUI or raises the
%      existing singleton*.  Starting from the left, property value pairs are
%      applied to the GUI before GA_gui_OpeningFcn gets called.  An
%      unrecognized property name or invalid value makes property application
%      stop.  All inputs are passed to GA_gui_OpeningFcn via varargin.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Edit the above text to modify the response to help GA_gui

% Last Modified by GUIDE v2.5 02-Aug-2015 16:49:27

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @GA_gui_OpeningFcn, ...
                   'gui_OutputFcn',  @GA_gui_OutputFcn, ...
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


% --- Executes just before GA_gui is made visible.
function GA_gui_OpeningFcn(hObject, eventdata, handles, varargin)
% This function has no output args, see OutputFcn.
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
% varargin   command line arguments to GA_gui (see VARARGIN)

% Choose default command line output for GA_gui
handles.output = hObject;

% Update handles structure
guidata(hObject, handles);

% UIWAIT makes GA_gui wait for user response (see UIRESUME)
% uiwait(handles.figure1);


% --- Outputs from this function are returned to the command line.
function varargout = GA_gui_OutputFcn(hObject, eventdata, handles) 
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


% --- Executes on selection change in popupmenu1.
function popupmenu1_Callback(hObject, eventdata, handles)
% hObject    handle to popupmenu1 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: contents = cellstr(get(hObject,'String')) returns popupmenu1 contents as cell array
%        contents{get(hObject,'Value')} returns selected item from popupmenu1


% --- Executes during object creation, after setting all properties.
function popupmenu1_CreateFcn(hObject, eventdata, handles)
% hObject    handle to popupmenu1 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: popupmenu controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on button press in GA_button.
function GA_button_Callback(hObject, eventdata, handles)
% hObject    handle to GA_button (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
	h = findobj('Tag','crossover_slider');
	crossover_rate = h.UserData;
    h = findobj('Tag','max_num_gen_slider');
	max_num_gen = h.UserData;
	h = findobj('Tag','pop_size_slider');
	pop_size = h.UserData;
    set(handles.plotdescription,'String','The best cost solution is shown in blue');
    set(handles.soln, 'String', ' ');
    set(handles.solnitems, 'String', ' ');
    set(handles.bestsoln,'String',' ');
    set(handles.numit,'String',' ');
    [results, solution] = GA(crossover_rate, max_num_gen, pop_size);
    
    set(handles.bestsoln,'String',num2str(results(1)));
    set(handles.numit,'String',num2str(results(2)));
    set(handles.plotdescription,'String','The best cost solution is shown in blue while the average solution is shown in red for each generation');
    set(handles.errormessage,'String',' ');
    
    route = solution('route');
    
    purchaseitems = solution('bestPurchaseArray');
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
set(hObject,'Max',50,'Min',10,'Value',10, 'SliderStep', [1/40 , 1/40]);


 val = get(hObject,'Value');   
set(hObject,'UserData',val);  

% --- Executes on slider movement.
function max_num_gen_slider_Callback(hObject, eventdata, handles)
% hObject    handle to max_num_gen_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
val = floor(get(hObject,'Value'));   
set(hObject,'UserData',val); 
set(handles.genval,'String',num2str(val));
% Hints: get(hObject,'Value') returns position of slider
%        get(hObject,'Min') and get(hObject,'Max') to determine range of slider


% --- Executes during object creation, after setting all properties.
function max_num_gen_slider_CreateFcn(hObject, eventdata, handles)
% hObject    handle to max_num_gen_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: slider controls usually have a light gray background.
if isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor',[.9 .9 .9]);
end
set(hObject,'Max',1000,'Min',10,'Value',500, 'SliderStep',[1/990 , 1/990 ]);
val = get(hObject,'Value');   
set(hObject,'UserData',val); 


% --- Executes on slider movement.
function crossover_slider_Callback(hObject, eventdata, handles)
% hObject    handle to crossover_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

val = get(hObject,'Value');   
set(hObject,'UserData',val);   
set(handles.crossval,'String',num2str(val));



% Hints: get(hObject,'Value') returns position of slider
%        get(hObject,'Min') and get(hObject,'Max') to determine range of slider


% --- Executes during object creation, after setting all properties.
function crossover_slider_CreateFcn(hObject, eventdata, handles)
% hObject    handle to crossover_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: slider controls usually have a light gray background.
if isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor',[.9 .9 .9]);
end
set(hObject,'Max',1.0,'Min',0.5,'Value',0.9);
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
    if val <= 10
        set(handles.pop_size_slider,'Value',10, 'UserData', 10);
        set(hObject,'String', num2str(10) );
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



function edit4_Callback(hObject, eventdata, handles)
% hObject    handle to edit4 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of edit4 as text
%        str2double(get(hObject,'String')) returns contents of edit4 as a double


% --- Executes during object creation, after setting all properties.
function edit4_CreateFcn(hObject, eventdata, handles)
% hObject    handle to edit4 (see GCBO)
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
    if val <= 10
        set(handles.max_num_gen_slider,'Value',10, 'UserData', 10);
        set(hObject,'String', num2str(10) );
    elseif val >= 1000
        set(handles.max_num_gen_slider,'Value',1000, 'UserData', 1000);
        set(hObject,'String', num2str(1000) );
    else 
        set(handles.max_num_gen_slider,'Value',floor(val), 'UserData', floor(val));
        set(hObject,'String', num2str(floor(val)));
    end
else 
    set(handles.errormessage,'String',strcat( valstr, ' is not a valid population number' ));
    h = findobj('Tag','max_num_gen_slider');
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



function crossval_Callback(hObject, eventdata, handles)
% hObject    handle to crossval (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of crossval as text
%        str2double(get(hObject,'String')) returns contents of crossval as a double

valstr = get(hObject,'String');  
if all(ismember(valstr, '0123456789+-.eEdD'))
    val = str2num(valstr);
    set(hObject,'UserData',floor(val));
    if val <= 0.5
        set(handles.crossover_slider,'Value',0.5, 'UserData', 0.5);
        set(hObject,'String', num2str(0.5) );
    elseif val >= 1.0
        set(handles.crossover_slider,'Value',1.0, 'UserData', 1.0);
        set(hObject,'String', num2str(1.0) );
    else 
        set(handles.crossover_slider,'Value',val, 'UserData', val);
        set(hObject,'String', num2str(val));
    end
else 
    set(handles.errormessage,'String',strcat( valstr, ' is not a valid crossover value' ));
    h = findobj('Tag','crossover_slider');
    val = h.UserData;
	set(hObject,'String', num2str(val));
end 

% --- Executes during object creation, after setting all properties.
function crossval_CreateFcn(hObject, eventdata, handles)
% hObject    handle to crossval (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end

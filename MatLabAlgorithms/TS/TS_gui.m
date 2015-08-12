function varargout = TS_gui(varargin)
% TS_GUI MATLAB code for TS_gui.fig
%      TS_GUI, by itself, creates a new TS_GUI or raises the existing
%      singleton*.
%
%      H = TS_GUI returns the handle to a new TS_GUI or the handle to
%      the existing singleton*.
%
%      TS_GUI('CALLBACK',hObject,eventData,handles,...) calls the local
%      function named CALLBACK in TS_GUI.M with the given input arguments.
%
%      TS_GUI('Property','Value',...) creates a new TS_GUI or raises the
%      existing singleton*.  Starting from the left, property value pairs are
%      applied to the GUI before TS_gui_OpeningFcn gets called.  An
%      unrecognized property name or invalid value makes property application
%      stop.  All inputs are passed to TS_gui_OpeningFcn via varargin.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Edit the above text to modify the response to help TS_gui

% Last Modified by GUIDE v2.5 03-Aug-2015 19:28:12

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @TS_gui_OpeningFcn, ...
                   'gui_OutputFcn',  @TS_gui_OutputFcn, ...
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


% --- Executes just before TS_gui is made visible.
function TS_gui_OpeningFcn(hObject, eventdata, handles, varargin)
% This function has no output args, see OutputFcn.
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
% varargin   command line arguments to TS_gui (see VARARGIN)

% Choose default command line output for TS_gui
handles.output = hObject;

% Update handles structure
guidata(hObject, handles);

% UIWAIT makes TS_gui wait for user response (see UIRESUME)
% uiwait(handles.figure1);


% --- Outputs from this function are returned to the command line.
function varargout = TS_gui_OutputFcn(hObject, eventdata, handles) 
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


% --- Executes on button press in TS_button.
function TS_button_Callback(hObject, eventdata, handles)
% hObject    handle to TS_button (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

    h = findobj('Tag','num_ants_slider');
    tabu_length = h.UserData;
	h = findobj('Tag','evap_rate_slider');
	max_gen = h.UserData;
    set(handles.plotdescription,'String','The best cost solution for each generation is shown in blue');
    set(handles.soln, 'String', ' ');
    set(handles.solnitems, 'String', ' ');
    set(handles.bestsoln,'String',' ');
    set(handles.numit,'String',' ');
    set(handles.errormessage, 'String', 'Running... ');

    [results, route, items] = TS(max_gen, tabu_length);
    
    set(handles.bestsoln,'String',num2str(results(1)));
    set(handles.numit,'String',num2str(results(2)));
    set(handles.errormessage,'String',' ');
    
    set(handles.soln, 'String', route);
    set(handles.solnitems, 'String', items);
    set(handles.errormessage, 'String', 'Finished Run');


% --- Executes on slider movement.
function evap_rate_slider_Callback(hObject, eventdata, handles)
% hObject    handle to evap_rate_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
val = get(hObject,'Value');   
set(hObject,'UserData',val); 
set(handles.tempval,'String',num2str(val));
% Hints: get(hObject,'Value') returns position of slider
%        get(hObject,'Min') and get(hObject,'Max') to determine range of slider


% --- Executes during object creation, after setting all properties.
function evap_rate_slider_CreateFcn(hObject, eventdata, handles)
% hObject    handle to evap_rate_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: slider controls usually have a light gray background.
if isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor',[.9 .9 .9]);
end
set(hObject,'Max',500,'Min',50,'Value',50,  'SliderStep',[1/450 , 1/450 ]);


 val = get(hObject,'Value');   
set(hObject,'UserData',val);  

% --- Executes on slider movement.
function num_ants_slider_Callback(hObject, eventdata, handles)
% hObject    handle to num_ants_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
val = floor(get(hObject,'Value'));   
set(hObject,'UserData',val); 
set(handles.genval,'String',num2str(val));
% Hints: get(hObject,'Value') returns position of slider
%        get(hObject,'Min') and get(hObject,'Max') to determine range of slider


% --- Executes during object creation, after setting all properties.
function num_ants_slider_CreateFcn(hObject, eventdata, handles)
% hObject    handle to num_ants_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: slider controls usually have a light gray background.
if isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor',[.9 .9 .9]);
end
set(hObject,'Max',50,'Min',5,'Value',5, 'SliderStep',[1/45 , 1/45 ]);
val = get(hObject,'Value');   
set(hObject,'UserData',val); 


% --- Executes on slider movement.
function alpha_slider_Callback(hObject, eventdata, handles)
% hObject    handle to alpha_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

val = get(hObject,'Value');   
set(hObject,'UserData',val);   
set(handles.alphaval,'String',num2str(val));



% Hints: get(hObject,'Value') returns position of slider
%        get(hObject,'Min') and get(hObject,'Max') to determine range of slider


% --- Executes during object creation, after setting all properties.
function alpha_slider_CreateFcn(hObject, eventdata, handles)
% hObject    handle to alpha_slider (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: slider controls usually have a light gray background.
if isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor',[.9 .9 .9]);
end
set(hObject,'Max',1.5,'Min',0.5,'Value',1.0);
val = get(hObject,'Value');   
set(hObject,'UserData',val); 
%set(hObject,'SliderStep',[ 0.1])



function tempval_Callback(hObject, eventdata, handles)
%evaporation rate
% hObject    handle to tempval (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
valstr = get(hObject,'String');  
min = 50;
max = 500;
if all(ismember(valstr, '0123456789+-.eEdD'))
    val = str2num(valstr);
    set(hObject,'UserData',floor(val));
    if val <= min
        set(handles.evap_rate_slider,'Value',min, 'UserData', min);
        set(hObject,'String', num2str(min) );
    elseif val >= max
        set(handles.evap_rate_slider,'Value',max, 'UserData', max);
        set(hObject,'String', num2str(max) );
    else 
        set(handles.evap_rate_slider,'Value',floor(val), 'UserData', floor(val));
        set(hObject,'String', num2str(floor(val)));
    end
else 
    set(handles.errormessage,'String',strcat( valstr, ' is not a valid number of generations' ));
    h = findobj('Tag','init_temp_slider');
    val = h.UserData;
	set(hObject,'String', num2str(val));
end 
% Hints: get(hObject,'String') returns contents of tempval as text
%        str2double(get(hObject,'String')) returns contents of tempval as a double


% --- Executes during object creation, after setting all properties.
function tempval_CreateFcn(hObject, eventdata, handles)
% hObject    handle to tempval (see GCBO)
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
min = 5;
max = 50;
if all(ismember(valstr, '0123456789+-.eEdD'))
    val = str2num(valstr);
    set(hObject,'UserData',floor(val));
    if val <= min
        set(handles.num_ants_slider,'Value',min, 'UserData', min);
        set(hObject,'String', num2str(min) );
    elseif val >= max
        set(handles.num_ants_slider,'Value',max, 'UserData', max);
        set(hObject,'String', num2str(max) );
    else 
        set(handles.num_ants_slider,'Value',floor(val), 'UserData', floor(val));
        set(hObject,'String', num2str(floor(val)));
    end
else 
    set(handles.errormessage,'String',strcat( valstr, ' is not a valid tabu list length' ));
    h = findobj('Tag','num_ants_slider');
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



function alphaval_Callback(hObject, eventdata, handles)
% hObject    handle to alphaval (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of alphaval as text
%        str2double(get(hObject,'String')) returns contents of alphaval as a double

valstr = get(hObject,'String');  
if all(ismember(valstr, '0123456789+-.eEdD'))
    val = str2num(valstr);
    set(hObject,'UserData',floor(val));
    if val <= 0.5
        set(handles.alpha_slider,'Value',0.5, 'UserData', 0.5);
        set(hObject,'String', num2str(0.5) );
    elseif val >= 1.5
        set(handles.alpha_slider,'Value',1.5, 'UserData', 1.5);
        set(hObject,'String', num2str(1.5) );
    else 
        set(handles.alpha_slider,'Value',val, 'UserData', val);
        set(hObject,'String', num2str(val));
    end
else 
    set(handles.errormessage,'String',strcat( valstr, ' is not a valid alpha value' ));
    h = findobj('Tag','alpha_slider');
    val = h.UserData;
	set(hObject,'String', num2str(val));
end 

% --- Executes during object creation, after setting all properties.
function alphaval_CreateFcn(hObject, eventdata, handles)
% hObject    handle to alphaval (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end
